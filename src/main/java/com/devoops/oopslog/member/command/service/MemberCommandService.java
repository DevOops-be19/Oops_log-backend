package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.ModifyDTO;
import com.devoops.oopslog.member.command.dto.TemporaryPwResponseDTO;
import com.devoops.oopslog.member.command.dto.VerifyPwDTO;
import com.devoops.oopslog.member.query.dto.FindPwDTO;
import com.devoops.oopslog.member.query.dto.FindPwResponseDTO;
import com.devoops.oopslog.member.command.dto.SignUpDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberCommandService extends UserDetailsService {
    void signUpMember(SignUpDTO signUpDTO);

    TemporaryPwResponseDTO verifyPw(VerifyPwDTO verifyPwDTO);

    void saveLoginHistory(Long id, String ipAddress, Character isSucceed);

    void modifyMemberInfo(ModifyDTO modifyDTO);

    void stopMember(Long id);
}
