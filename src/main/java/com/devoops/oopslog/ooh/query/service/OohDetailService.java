package com.devoops.oopslog.ooh.query.service;

import com.devoops.oopslog.ooh.query.dto.OohDetailDTO;
import com.devoops.oopslog.ooh.query.mapper.OohDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OohDetailService {
    private final OohDetailMapper mapper;

    public OohDetailDTO getDetail(Long oohId, int commentLimit /*, Long meId*/) {
        OohDetailDTO base = mapper.selectOohBaseById(oohId);
        if (base == null) return null;

        base.setTags(mapper.selectTagNamesByOohId(oohId));
        base.setEmotions(mapper.selectEmotionNamesByOohId(oohId));
        Long likesCnt = mapper.selectLikesCountByOohId(oohId);
        base.setLikesCount(likesCnt == null ? 0 : likesCnt);

        base.setComments(mapper.selectCommentsByOohId(oohId, commentLimit));
        Long total = mapper.selectCommentsTotal(oohId);
        base.setCommentsTotal(total == null ? 0 : total);

        // exist(좋아요 내가 눌렀는지)는 당장은 사용 안 함
        base.setLikedByMe(false);

        return base;
    }
}
