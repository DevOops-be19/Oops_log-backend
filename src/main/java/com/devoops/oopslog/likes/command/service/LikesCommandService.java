package com.devoops.oopslog.likes.command.service;

public interface LikesCommandService {
    String createOrDeleteLikesByOopsId(int oopsId, long userId);

    String createOrDeleteLikesByOohId(int oohId, long userId);

    String isOopsLikeExist(int oopsId, long userId);

    String isOohLikeExist(int oohId, long userId);
}
