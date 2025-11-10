package com.devoops.oopslog.likes.query.service;

import com.devoops.oopslog.likes.query.mapper.LikesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesQueryServiceImpl implements LikesQueryService {
    private LikesMapper likesMapper;

    @Autowired
    public LikesQueryServiceImpl(LikesMapper likesMapper) {
        this.likesMapper = likesMapper;
    }

    @Override
    public int oopsLikesCount(int oopsId) {
        int count = likesMapper.countOopsLikes(oopsId);
        return count;
    }

    @Override
    public int oohLikesCount(int oohId) {
        int count = likesMapper.countOohLikes(oohId);
        return count;
    }
}
