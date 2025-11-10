package com.devoops.oopslog.likes.query.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesMapper {
    int countOopsLikes(int oopsId);

    int countOohLikes(int oohId);
}
