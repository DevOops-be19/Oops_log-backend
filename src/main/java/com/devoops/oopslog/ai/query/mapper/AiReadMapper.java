package com.devoops.oopslog.ai.query.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AiReadMapper {
    List<String> emoTag();
}
