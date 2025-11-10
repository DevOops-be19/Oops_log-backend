package com.devoops.oopslog.comments.query.mapper;

import com.devoops.oopslog.comments.query.dto.CommentsQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {
    List<CommentsQueryDTO> selectCommentsByOohId(int oohId);

    List<CommentsQueryDTO> selectCommentsByOopsId(int oopsId);

    List<CommentsQueryDTO> selectCommentsByNoticeId(int noticeId);
}
