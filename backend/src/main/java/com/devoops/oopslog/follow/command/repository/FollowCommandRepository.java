package com.devoops.oopslog.follow.command.repository;

import com.devoops.oopslog.follow.command.entity.Follow;
import com.devoops.oopslog.member.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FollowCommandRepository extends JpaRepository<Follow, Long> {

    // Create 시 중복 확인
    boolean existsByFollowerAndFollowee(Member follower, Member followee);

    // Delete
    @Transactional
    void deleteByFollowerAndFollowee(Member follower, Member followee);
}