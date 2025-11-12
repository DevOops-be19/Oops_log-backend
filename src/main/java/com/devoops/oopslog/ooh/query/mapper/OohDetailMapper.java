package com.devoops.oopslog.ooh.query.mapper;

import com.devoops.oopslog.ooh.query.dto.OohDetailCommentDTO;
import com.devoops.oopslog.ooh.query.dto.OohDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OohDetailMapper {

    // 부모(본문 + 작성자)
    OohDetailDTO selectOohBaseById(@Param("oohId") Long oohId);

    // 서브조회들
    List<String> selectTagNamesByOohId(@Param("oohId") Long oohId);
    List<String> selectEmotionNamesByOohId(@Param("oohId") Long oohId);

    Long selectLikesCountByOohId(@Param("oohId") Long oohId);

    List<OohDetailCommentDTO> selectCommentsByOohId(
            @Param("oohId") Long oohId,
            @Param("limit") int limit);

    Long selectCommentsTotal(@Param("oohId") Long oohId);
}