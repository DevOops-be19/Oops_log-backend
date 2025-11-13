package com.devoops.oopslog.oops.query.mapper;

import com.devoops.oopslog.oops.query.dto.OopsDetailCommentDTO;
import com.devoops.oopslog.oops.query.dto.OopsDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OopsDetailMapper {

    // 부모(본문 + 작성자)
    OopsDetailDTO selectOopsBaseById(@Param("oopsId") Long oopsId);

    // 서브조회들
    List<String> selectTagNamesByOopsId(@Param("oopsId") Long oopsId);
    List<String> selectEmotionNamesByOopsId(@Param("oopsId") Long oopsId);

    Long selectLikesCountByOopsId(@Param("oopsId") Long oopsId);

    List<OopsDetailCommentDTO> selectCommentsByOopsId(
            @Param("oopsId") Long oopsId,
            @Param("limit") int limit);

    Long selectCommentsTotal(@Param("oopsId") Long oopsId);
}