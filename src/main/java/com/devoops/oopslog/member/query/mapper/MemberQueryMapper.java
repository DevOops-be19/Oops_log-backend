package com.devoops.oopslog.member.query.mapper;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.FindMemberDTO;
import com.devoops.oopslog.member.query.dto.FindPwDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberQueryMapper {
    FindMemberDTO findMemberById(FindIdDTO findIdDTO);

    FindMemberDTO findMemberPwById(FindPwDTO findPwDTO);

    List<Map<String, String>> getAuthList(Long userId);
}
