package com.devoops.oopslog.follow;

import com.devoops.oopslog.follow.command.entity.Follow;
import com.devoops.oopslog.follow.command.repository.FollowCommandRepository;
import com.devoops.oopslog.follow.command.service.FollowCommandService;
import com.devoops.oopslog.follow.query.dto.FollowerResponseDto;
import com.devoops.oopslog.follow.query.service.FollowQueryService;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback // 테스트 후 DB 롤백 (데이터 오염 방지)
public class FollowServiceTest {

    @Autowired
    private FollowCommandService followCommandService;

    @Autowired
    private FollowQueryService followQueryService;

    // --- CUD 및 데이터 검증을 위한 의존성 ---
    @Autowired
    private FollowCommandRepository followCommandRepository;

    @Autowired
    private MemberCommandRepository memberCommandRepository;

    // 테스트용 더미 데이터
    private Member testUser1;
    private Member testUser2;
    private Member testUser3;

    @BeforeEach
    void setUp() {
        // 테스트 격리를 위해 매번 follow 테이블을 비움
        followCommandRepository.deleteAllInBatch();

        // CUD 테스트를 위한 기본 데이터 생성 (ID 1, 2, 3가 이미 존재한다고 가정)
        testUser1 = memberCommandRepository.findById(1L).orElse(null);
        testUser2 = memberCommandRepository.findById(2L).orElse(null);
        testUser3 = memberCommandRepository.findById(3L).orElse(null);

        // 더미 데이터가 없을 경우를 대비한 방어 코드
        if (testUser1 == null) {
            testUser1 = new Member();
            testUser1.setMemberId("testUser1");
            testUser1.setMemberPw("password");
            testUser1.setName("테스트유저1");
            testUser1 = memberCommandRepository.save(testUser1);
        }
        if (testUser2 == null) {
            testUser2 = new Member();
            testUser2.setMemberId("testUser2");
            testUser2.setMemberPw("password");
            testUser2.setName("테스트유저2");
            testUser2 = memberCommandRepository.save(testUser2);
        }
        if (testUser3 == null) {
            testUser3 = new Member();
            testUser3.setMemberId("testUser3");
            testUser3.setMemberPw("password");
            testUser3.setName("테스트유저3");
            testUser3 = memberCommandRepository.save(testUser3);
        }

        // '언팔로우' 및 '목록 조회' 테스트를 위한 사전 데이터 1개 추가
        Follow existingFollow = new Follow(testUser2, testUser3);
        followCommandRepository.save(existingFollow);
    }

    // --- Command (CUD) 테스트 ---

    @Test
    @DisplayName("[C] 팔로우 추가 테스트")
    void testFollowSuccess() {
        // given: 유저 1이 유저 2를 팔로우
        long followerId = testUser1.getId();
        long followeeId = testUser2.getId();

        // (setUp에서 1건만 save 했으므로 countBefore는 1이 되어야 함)
        long countBefore = followCommandRepository.count();
        assertThat(countBefore).isEqualTo(1);

        // when
        Assertions.assertDoesNotThrow(
                () -> followCommandService.follow(followerId, followeeId)
        );

        // then
        long countAfter = followCommandRepository.count();
        assertThat(countAfter).isEqualTo(2); // (1 + 1 = 2)
        boolean isFollowing = followCommandRepository.existsByFollowerAndFollowee(testUser1, testUser2);
        Assertions.assertTrue(isFollowing);
    }

    @Test
    @DisplayName("[C] 이미 팔로우 중인 유저를 팔로우할 때 예외 발생 테스트")
    void testFollowAlreadyExistsFailure() {
        // given: 유저 2가 유저 3을 (이미) 팔로우 (setUp에서 완료)
        long followerId = testUser2.getId();
        long followeeId = testUser3.getId();

        // when & then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            followCommandService.follow(followerId, followeeId);
        }, "이미 팔로우 중인 사용자입니다.");
    }

    @Test
    @DisplayName("[D] 언팔로우 성공 테스트")
    void testUnfollowSuccess() {
        // given: 유저 2가 유저 3을 팔로우 중 (Setup에서 완료)
        long followerId = testUser2.getId();
        long followeeId = testUser3.getId();

        // (setUp에서 1건만 save 했으므로 countBefore는 1이 되어야 함)
        long countBefore = followCommandRepository.count();
        assertThat(countBefore).isEqualTo(1);

        // when
        Assertions.assertDoesNotThrow(
                () -> followCommandService.unfollow(followerId, followeeId)
        );

        // then
        long countAfter = followCommandRepository.count();
        assertThat(countAfter).isEqualTo(0); // (1 - 1 = 0)

        // 기존 검증 로직도 통과
        assertThat(countAfter).isEqualTo(countBefore - 1);

        boolean isFollowing = followCommandRepository.existsByFollowerAndFollowee(testUser2, testUser3);
        Assertions.assertFalse(isFollowing);
    }

    @Test
    @DisplayName("[D] 팔로우하지 않는 유저를 언팔로우할 때 예외 발생 테스트")
    void testUnfollowNotFollowingFailure() {
        // given: 유저 1이 유저 3을 팔로우하지 않음
        long followerId = testUser1.getId();
        long followeeId = testUser3.getId();

        // when & then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            followCommandService.unfollow(followerId, followeeId);
        }, "팔로우 중인 사용자가 아닙니다.");
    }

    // --- Query (R) 테스트 ---

    @Test
    @DisplayName("[R] 특정 유저의 팔로잉(following) 목록 조회 테스트")
    void testGetFollowingList() {
        // given: 유저 2는 유저 3을 팔로우 중 (Setup에서 1건만 완료)

        // when
        List<FollowerResponseDto> followingList = Assertions.assertDoesNotThrow(
                () -> followQueryService.getFollowingList(testUser2.getId())
        );

        // then
        Assertions.assertNotNull(followingList);
        assertThat(followingList.size()).isEqualTo(1); // (이제 정확히 1건만 조회됨)
        Assertions.assertEquals(testUser3.getName(), followingList.get(0).getName());
    }

    @Test
    @DisplayName("[R] 특정 유저의 팔로워(follower) 목록 조회 테스트")
    void testGetFollowerList() {
        // given: 유저 3은 유저 2에게 팔로우당하는 중 (Setup에서 1건만 완료)

        // when
        List<FollowerResponseDto> followerList = Assertions.assertDoesNotThrow(
                () -> followQueryService.getFollowerList(testUser3.getId())
        );

        // then
        Assertions.assertNotNull(followerList);
        assertThat(followerList.size()).isEqualTo(1); // (이제 정확히 1건만 조회됨)
        Assertions.assertEquals(testUser2.getName(), followerList.get(0).getName());
    }
}