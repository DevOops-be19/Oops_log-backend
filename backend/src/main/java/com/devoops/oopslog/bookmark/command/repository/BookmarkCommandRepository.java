package com.devoops.oopslog.bookmark.command.repository;

import com.devoops.oopslog.bookmark.command.entity.Bookmark;
import com.devoops.oopslog.member.command.entity.Member;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookmarkCommandRepository extends JpaRepository<Bookmark, Long> {

    // Create 시 중복 확인
    boolean existsByUserAndOopsRecord(Member user, OopsCommandEntity oopsRecord);
    boolean existsByUserAndOohRecord(Member user, OohCommandEntity oohRecord);

    // Delete
    @Transactional
    void deleteByUserAndOopsRecord(Member user, OopsCommandEntity oopsRecord);
    @Transactional
    void deleteByUserAndOohRecord(Member user, OohCommandEntity oohRecord);
}
