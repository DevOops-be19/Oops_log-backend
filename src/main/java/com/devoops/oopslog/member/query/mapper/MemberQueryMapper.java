package com.devoops.oopslog.member.query.mapper;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.FindMemberDTO;
import com.devoops.oopslog.member.query.dto.FindPwDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberQueryMapper {
    FindMemberDTO findMemberById(FindIdDTO findIdDTO);

    FindMemberDTO findMemberPwById(FindPwDTO findPwDTO);
}
