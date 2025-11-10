package com.devoops.oopslog.member.query.service;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.FindPwDTO;
import com.devoops.oopslog.member.query.dto.FindPwResponseDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MemberQueryService {
    void findMemberId(FindIdDTO findIdDTO) throws MessagingException, UnsupportedEncodingException;

    FindPwResponseDTO findMemberPw(FindPwDTO findPwDTO) throws MessagingException, UnsupportedEncodingException;
}
