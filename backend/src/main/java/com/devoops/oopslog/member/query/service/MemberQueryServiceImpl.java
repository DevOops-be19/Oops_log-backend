package com.devoops.oopslog.member.query.service;

import com.devoops.oopslog.member.query.dto.*;
import com.devoops.oopslog.member.query.mapper.MemberQueryMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberQueryMapper memberQueryMapper;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String,String> redisTemplate;

    public MemberQueryServiceImpl(MemberQueryMapper memberQueryMapper,
                                  JavaMailSender mailSender,
                                  RedisTemplate<String,String> redisTemplate) {
        this.memberQueryMapper = memberQueryMapper;
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void findMemberId(FindIdDTO findIdDTO) throws MessagingException, UnsupportedEncodingException {
        // DB 에서 일치하는 회원 조회
        FindMemberDTO findMemberDTO = memberQueryMapper.findMemberById(findIdDTO);
        log.info("findMemberDTO: {}",findMemberDTO);

        // 추후에 예외처리 필요
        // 일치하는 회원 정보가 없을 시
        if(findMemberDTO == null){
//            throw new RuntimeException("일치하는 회원 정보가 없습니다.");
        }

        // 일치하는 회원이 있다면 메일로 id 발신
        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(findMemberDTO.getEmail());
        mailDTO.setSubject("요청하신 아이디입니다.");
        mailDTO.setContent("<h2>요청하신 아이디입니다.</h2><br><hr><br><p>회원님이 요청하신 아이디는 </p><br><b>"+findMemberDTO.getMemberId()+"</b><br><br><p>입니다.</p>");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setTo(mailDTO.getAddress());                 // 받는 사람 이메일
        mimeMessageHelper.setSubject(mailDTO.getSubject());            // 이메일 제목
        mimeMessageHelper.setText(mailDTO.getContent(),true);     // 이메일 내용
        try {
            mimeMessageHelper.setFrom(new InternetAddress("rptmffld0204@gmail.com","Oops_log"));    // 제 이메일 주소입니다......
            mailSender.send(mimeMessage);                        // 메일 보내기
            log.info("이메일 전송 성공!");
        } catch (MailException e) {
            log.info("[-] 이메일 전송중에 오류가 발생하였습니다 {}", e.getMessage());
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.info("[-] 지원되지 않는 형식입니다. {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public FindPwResponseDTO findMemberPw(FindPwDTO findPwDTO) throws MessagingException, UnsupportedEncodingException {
        // DB 에서 일치하는 회원 조회
        FindMemberDTO findMemberDTO = memberQueryMapper.findMemberPwById(findPwDTO);
        log.info("findMemberDTO: {}",findMemberDTO);

        // 추후에 예외처리 필요
        // 일치하는 회원 정보가 없을 시
        if(findMemberDTO == null){
            throw new RuntimeException("일치하는 회원 정보가 없습니다.");
        }

        // redis에 key:value를 email:RandomNumber 로 가지는 데이터 생성(with TTL)
        String ranNum = randomNum();
        log.info("생성된 난수값: {}",ranNum);
        redisTemplate.opsForValue().set(findMemberDTO.getEmail(), ranNum,3, TimeUnit.MINUTES);     // (key(email), value(랜덤값), 시간 설정, 초/분/시 설정)

        // 일치하는 회원이 있다면 메일로 인증번호 발신
        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(findMemberDTO.getEmail());
        mailDTO.setSubject("요청하신 인증번호 입니다.");
        mailDTO.setContent("<h2>요청하신 인증번호 입니다.</h2><hr><br><p>회원님이 요청하신 인증번호는 </p><br><b>"+ranNum+"</b><br><br><p>입니다.</p><br><p>3분 내로 인증번호를 입력해주시길 바랍니다.</p><br><p>인증이 완료 되면 임시 비밀번호로 로그인 후 비밀번호를 변경해 주십시오.</p>");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setTo(mailDTO.getAddress());                 // 받는 사람 이메일
        mimeMessageHelper.setSubject(mailDTO.getSubject());            // 이메일 제목
        mimeMessageHelper.setText(mailDTO.getContent(),true);     // 이메일 내용
        try {
            mimeMessageHelper.setFrom(new InternetAddress("rptmffld0204@gmail.com","Oops_log"));    // 제 이메일 주소입니다......
            mailSender.send(mimeMessage);                        // 메일 보내기
            log.info("이메일 전송 성공!");
        } catch (MailException e) {
            log.info("[-] 이메일 전송중에 오류가 발생하였습니다 {}", e.getMessage());
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.info("[-] 지원되지 않는 형식입니다. {}", e.getMessage());
            throw e;
        }
        return null;
    }

    private String randomNum() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}
