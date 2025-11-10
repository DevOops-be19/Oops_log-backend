package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.*;
import com.devoops.oopslog.member.command.entity.LoginHistory;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.LoginHistoryCommandRepository;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberCommandRepository memberCommandRepository;
    private final LoginHistoryCommandRepository loginHistoryCommandRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public MemberCommandServiceImpl(MemberCommandRepository memberCommandRepository,
                                    BCryptPasswordEncoder bCryptPasswordEncoder,
                                    ModelMapper modelMapper, RedisTemplate<String, String> redisTemplate,
                                    LoginHistoryCommandRepository loginHistoryCommandRepository) {
        this.memberCommandRepository = memberCommandRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
        this.loginHistoryCommandRepository = loginHistoryCommandRepository;
    }

    @Override
    @Transactional
    public void signUpMember(SignUpDTO signUpDTO) {
        LocalDateTime now = LocalDateTime.now();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 기본값 세팅
        Member member = modelMapper.map(signUpDTO, Member.class);
        member.setUser_state('A');
        member.setSign_up_date(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        member.setMemberPw(bCryptPasswordEncoder.encode(member.getMemberPw()));

        memberCommandRepository.save(member);
    }

    @Override
    @Transactional
    public TemporaryPwResponseDTO verifyPw(VerifyPwDTO verifyPwDTO) {
        // 인증번호 검사
        String savedVerifyCode = redisTemplate.opsForValue().get(verifyPwDTO.getEmail());
        log.info("클라이언트,서버 인증코드: {}, {}", verifyPwDTO.getVerifyCode(), savedVerifyCode);
        if (savedVerifyCode == null) {
            throw new IllegalArgumentException("인증번호가 만료되었거나 존재하지 않습니다.");
        }
        if (!savedVerifyCode.equals(verifyPwDTO.getVerifyCode())) {
            throw new RuntimeException("인증번호가 다릅니다.");
        }
        log.info("인증번호 확인 완료");

        //redis 에서 인증번호 삭제
        redisTemplate.delete(verifyPwDTO.getEmail());

        // member 에서 일치하는 데이터 가져오기
        Member member = memberCommandRepository.findByEmail(verifyPwDTO.getEmail());

        // 랜덤 난수(8자리로 자름)
        UUID uuid = UUID.randomUUID();
        String temPw = uuid.toString().substring(0, 8);
        log.info("임시 비밀번호: {}", temPw);
        member.setMemberPw(bCryptPasswordEncoder.encode(temPw));

        return new TemporaryPwResponseDTO(temPw);
    }

    @Override
//    @Async
    @Transactional
    public void saveLoginHistory(Long id, String ipAddress, Character isSucceed) {
        LocalDateTime now = LocalDateTime.now();
        LoginHistory loginHistory = new LoginHistory();

        loginHistory.setLogin_ip(ipAddress);
        loginHistory.setLogin_is_succeed(isSucceed);
        loginHistory.setLogin_success_date(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        loginHistory.setUser_id(id);
        log.info("로그인 이력: {}",loginHistory);
        loginHistoryCommandRepository.save(loginHistory);
    }

    @Override
    @Transactional
    public void modifyMemberInfo(ModifyDTO modifyDTO) {
        Member member = memberCommandRepository.findById(modifyDTO.getId()).get();
        modifyDTO.setMemberPw(bCryptPasswordEncoder.encode(modifyDTO.getMemberPw()));
        log.info("수정할 member 엔티티: {}",member);

        if(!member.getMemberId().equals(modifyDTO.getMemberId())) {
            member.setMemberId(modifyDTO.getMemberId());
        }
        if(!member.getMemberPw().equals(modifyDTO.getMemberPw())) {
            member.setMemberPw(modifyDTO.getMemberPw());
        }
        if(!member.getEmail().equals(modifyDTO.getEmail())) {
            member.setEmail(modifyDTO.getEmail());
        }
    }

    @Override
    @Transactional
    public void stopMember(Long id) {
        Member member = memberCommandRepository.findById(id).get();
        memberCommandRepository.delete(member);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberCommandRepository.findByMemberId(username);

        // 존재하지 않는 아이디
        if (member == null) throw new UsernameNotFoundException(username + " - 회원정보가 존재하지 않음");

        // 정지된 회원
        if (member.getUser_state() == 'S') throw new LockedException(username + " - 정지된 회원");

        // 5회 이상 로그인 시도


        // 회원 권한 꺼내기
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 커스텀한 User 객체 이용
        UserImpl userImpl = new UserImpl(member.getMemberId(), member.getMemberPw(), grantedAuthorities);
        userImpl.setUserInfo(new UserDetailInfoDTO(member.getId()));
        return userImpl;
        // 사용자의 id,pw,권한,하위 정보들을 provider로 전송
//        return new User(member.getMemberId(), member.getMemberPw(), true, true, true, true, grantedAuthorities);
    }

}
