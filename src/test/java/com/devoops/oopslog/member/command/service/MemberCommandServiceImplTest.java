package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.*;
import com.devoops.oopslog.member.command.entity.LoginHistory;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.entity.UserAuth;
import com.devoops.oopslog.member.command.repository.LoginHistoryCommandRepository;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.member.command.repository.UserAuthCommandRepository;
import com.devoops.oopslog.member.query.service.MemberQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceImplTest {

    @Mock MemberCommandRepository memberCommandRepository;
    @Mock LoginHistoryCommandRepository loginHistoryCommandRepository;
    @Mock BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock RedisTemplate<String, String> redisTemplate;
    @Mock ValueOperations<String, String> valueOperations;
    @Mock UserAuthCommandRepository userAuthCommandRepository;
    @Mock MemberQueryService memberQueryService;

    @Mock ModelMapper modelMapper;
    @Mock Configuration modelMapperConfig;

    MemberCommandServiceImpl service;

    @BeforeEach
    void setup() {
        lenient().when(modelMapper.getConfiguration()).thenReturn(modelMapperConfig);
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        service = new MemberCommandServiceImpl(
                memberCommandRepository,
                bCryptPasswordEncoder,
                modelMapper,
                redisTemplate,
                loginHistoryCommandRepository,
                userAuthCommandRepository,
                memberQueryService
        );
    }

    // ---------------------------------------------------------
    // ✔ 회원가입 성공 테스트
    // ---------------------------------------------------------
    @Test
    void testSignUpMember() {

        SignUpDTO dto = new SignUpDTO();
        dto.setMemberId("user1");
        dto.setEmail("test@test.com");
        dto.setMemberPw("1234");

        Member member = new Member();
        member.setMemberId(dto.getMemberId());
        member.setEmail(dto.getEmail());
        member.setMemberPw(dto.getMemberPw());

        when(modelMapper.map(dto, Member.class)).thenReturn(member);

        // 중복없음
        when(memberCommandRepository.existsByMemberId("user1")).thenReturn(false);
        when(memberCommandRepository.existsByEmail("test@test.com")).thenReturn(false);

        // 비밀번호 암호화
        when(bCryptPasswordEncoder.encode("1234")).thenReturn("ENC_PW");

        // 저장 후 ID 부여
        member.setId(1L);
        when(memberCommandRepository.saveAndFlush(member)).thenReturn(member);

        service.signUpMember(dto);

        verify(memberCommandRepository, times(1)).saveAndFlush(member);
        verify(userAuthCommandRepository, times(1)).save(any(UserAuth.class));
    }


    // ---------------------------------------------------------
    // ✔ 임시 비밀번호 발급 테스트
    // ---------------------------------------------------------
    @Test
    void testVerifyPw() {
        VerifyPwDTO verifyPwDTO = new VerifyPwDTO();
        verifyPwDTO.setEmail("email@test.com");
        verifyPwDTO.setVerifyCode("7777");

        when(valueOperations.get("email@test.com")).thenReturn("7777");

        Member mem = new Member();
        mem.setEmail("email@test.com");
        when(memberCommandRepository.findByEmail("email@test.com")).thenReturn(mem);

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("ENC_TEMP");

        TemporaryPwResponseDTO res = service.verifyPw(verifyPwDTO);

        assertEquals(8, res.getNewPw().length());
        verify(redisTemplate, times(1)).delete("email@test.com");
    }


    // ---------------------------------------------------------
    // ✔ 로그인 인증 성공
    // ---------------------------------------------------------
    @Test
    void testLoadUserByUsername_Success() {

        Member mem = new Member();
        mem.setId(1L);
        mem.setMemberId("user1");
        mem.setMemberPw("ENC_PW");
        mem.setUser_state('A');

        when(memberCommandRepository.findByMemberId("user1")).thenReturn(mem);

        List<Map<String,String>> authList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("auth_name", "ROLE_USER");
        authList.add(map);

        when(memberQueryService.getAuthList(1L)).thenReturn(authList);

        var user = service.loadUserByUsername("user1");

        assertEquals("user1", user.getUsername());
        assertTrue(
                user.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
        );
    }


    // ---------------------------------------------------------
    // ✔ 로그인 인증 실패 - 회원 없음
    // ---------------------------------------------------------
    @Test
    void testLoadUserByUsername_NotFound() {
        when(memberCommandRepository.findByMemberId("noUser")).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("noUser")
        );
    }


    // ---------------------------------------------------------
    // ✔ 로그인 인증 실패 - 정지 회원
    // ---------------------------------------------------------
    @Test
    void testLoadUserByUsername_Stopped() {
        Member mem = new Member();
        mem.setUser_state('S');

        when(memberCommandRepository.findByMemberId("stopUser")).thenReturn(mem);

        assertThrows(
                LockedException.class,
                () -> service.loadUserByUsername("stopUser")
        );
    }
}
