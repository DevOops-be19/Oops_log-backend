package com.devoops.oopslog.likes.command.repository;

import com.devoops.oopslog.likes.command.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByOopsId(Long oopsId);

    Likes findByOohId(Long oohId);
}
