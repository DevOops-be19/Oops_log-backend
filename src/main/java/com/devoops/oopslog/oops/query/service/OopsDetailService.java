package com.devoops.oopslog.oops.query.service;

import com.devoops.oopslog.oops.query.dto.OopsDetailDTO;
import com.devoops.oopslog.oops.query.mapper.OopsDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OopsDetailService {
    private final OopsDetailMapper mapper;

    public OopsDetailDTO getDetail(Long oopsId, int commentLimit /*, Long meId*/) {
        OopsDetailDTO base = mapper.selectOopsBaseById(oopsId);
        if (base == null) return null;

        base.setTags(mapper.selectTagNamesByOopsId(oopsId));
        base.setEmotions(mapper.selectEmotionNamesByOopsId(oopsId));
        Long likesCnt = mapper.selectLikesCountByOopsId(oopsId);
        base.setLikesCount(likesCnt == null ? 0 : likesCnt);

        base.setComments(mapper.selectCommentsByOopsId(oopsId, commentLimit));
        Long total = mapper.selectCommentsTotal(oopsId);
        base.setCommentsTotal(total == null ? 0 : total);

        // exist(좋아요 내가 눌렀는지)는 당장은 사용 안 함
        base.setLikedByMe(false);

        return base;
    }
}
