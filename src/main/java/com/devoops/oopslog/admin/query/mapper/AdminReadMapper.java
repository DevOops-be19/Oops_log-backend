package com.devoops.oopslog.admin.query.mapper;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import com.devoops.oopslog.admin.query.dto.AllTagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminReadMapper {
    List<AllMemberDTO> selectAllMember(@Param("limit") int limit, @Param("offset") int offset);
    List<AllTagDTO> selectAllTag();
}
