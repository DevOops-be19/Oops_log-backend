package com.devoops.oopslog.follow.command.service;

import com.devoops.oopslog.follow.command.entity.Follow;
import com.devoops.oopslog.follow.command.repository.FollowCommandRepository;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowCommandService {

    // CUD용 JPA 리포지토리
    private final FollowCommandRepository followCommandRepository;
    // Member 엔티티 조회를 위한 공통 리포지토리
    private final MemberCommandRepository memberCommandRepository;

    /**
     * 팔로우 C (JPA)
     */
    @Transactional
    public void follow(Long followerId, Long followeeId) {
        log.info("팔로우 시도: followerId={} -> followeeId={}", followerId, followeeId);
        if (followerId.equals(followeeId)) {
            throw new IllegalStateException("스스로를 팔로우할 수 없습니다.");
        }

        Member follower = memberCommandRepository.findById(followerId)
                .orElseThrow(() -> new NoSuchElementException("팔로우하는 사용자를 찾을 수 없습니다."));
        Member followee = memberCommandRepository.findById(followeeId)
                .orElseThrow(() -> new NoSuchElementException("팔로우 대상 사용자를 찾을 수 없습니다"));

        if (followCommandRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new IllegalStateException("이미 팔로우 중인 사용자입니다.");
        }

        Follow follow = new Follow(follower, followee);
        followCommandRepository.save(follow);

        log.info("팔로우 성공: followerId={} -> followeeId={}", followerId, followeeId);
    }

    /**
     * 언팔로우 D (JPA)
     */
    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        log.info("언팔로우 시도: followerId={} X followeeId={}", followerId, followeeId);
        Member follower = memberCommandRepository.findById(followerId)
                .orElseThrow(() -> new NoSuchElementException("언팔로우하는 사용자를 찾을 수 없습니다."));
        Member followee = memberCommandRepository.findById(followeeId)
                .orElseThrow(() -> new NoSuchElementException("언팔로우 대상 사용자를 찾을 수 없습니다."));

        if (!followCommandRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new IllegalStateException("팔로우 중인 사용자가 아닙니다.");
        }

        followCommandRepository.deleteByFollowerAndFollowee(follower, followee);

        log.info("언팔로우 성공: followerId={} X followeeId={}", followerId, followeeId);
    }
}