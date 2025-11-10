package com.devoops.oopslog.follow;

import com.devoops.oopslog.follow.command.dto.FollowRequestDto;
import com.devoops.oopslog.follow.command.repository.FollowCommandRepository;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // (1) 스프링 전체 컨텍스트 로드
@AutoConfigureMockMvc // (2) MockMvc 주입
//@Transactional // (3) 테스트 후 DB 롤백 (데이터 오염 방지)
class FollowFeatureTest {

    @Autowired
    private MockMvc mockMvc; // API 호출용

    @Autowired
    private ObjectMapper objectMapper; // DTO -> JSON 변환용

    @Autowired
    private FollowCommandRepository followCommandRepository; // CUD 검증용

    @Autowired
    private MemberCommandRepository memberCommandRepository; // 테스트 데이터 검증용

    // --- CUD (Command) 테스트 ---

    @Test
    @DisplayName("[C] 유저가 다른 유저를 팔로우한다 (JPA)")
    void followTest() throws Exception {
        // given
        // DUMMY DATA: 1번 유저(admin_oops)와 10번 유저(growth_mindset)는 현재 팔로우 관계 아님 [cite: 81]
        // Controller의 하드코딩(1L)을 이용해, 1번 유저가 10번 유저를 팔로우하는 시나리오
        FollowRequestDto requestDto = new FollowRequestDto();
        requestDto.setFolloweeId(10L); // 10번 유저를 팔로우

        long followCountBefore = followCommandRepository.count();

        // when
        mockMvc.perform(post("/api/v1/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()); // then 1

        // then 2
        long followCountAfter = followCommandRepository.count();
        assertThat(followCountAfter).isEqualTo(followCountBefore + 1);

        // then 3 (DB 직접 검증)
        var follower = memberCommandRepository.findById(1L).get();
        var followee = memberCommandRepository.findById(10L).get();
        boolean isFollowing = followCommandRepository.existsByFollowerAndFollowee(follower, followee);
        assertThat(isFollowing).isTrue();
    }

    @Test
    @DisplayName("[D] 유저가 팔로우를 취소한다 (JPA)")
    void unfollowTest() throws Exception {
        // given
        // DUMMY DATA: 2번 유저(dev_master)는 3번 유저(design_guru)를 팔로우 중 [cite: 81]
        // ** 중요 **: 이 테스트는 Controller의 followerId=1L 하드코딩 문제를 해결해야 통과합니다.
        //           (임시로 1번 유저가 2번 유저를 팔로우하는 데이터를 넣고 테스트)

        // (Test-Setup) 1번 유저가 2번 유저를 팔로우하도록 미리 저장
        var follower = memberCommandRepository.findById(2L).get();
        var followee = memberCommandRepository.findById(10L).get();
        followCommandRepository.save(new com.devoops.oopslog.follow.command.entity.Follow(follower, followee));

        long followCountBefore = followCommandRepository.count();

        FollowRequestDto requestDto = new FollowRequestDto();
        requestDto.setFolloweeId(10L); // 1번 유저가 2번 유저를 언팔로우

        // when
        mockMvc.perform(delete("/api/v1/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()); // then 1

        // then 2
        long followCountAfter = followCommandRepository.count();
        assertThat(followCountAfter).isEqualTo(followCountBefore - 1);

        // then 3 (DB 직접 검증)
        boolean isFollowing = followCommandRepository.existsByFollowerAndFollowee(follower, followee);
        assertThat(isFollowing).isFalse();
    }

    // --- R (Query) 테스트 ---

    @Test
    @DisplayName("[R] 특정 유저의 팔로잉 목록을 조회한다 (MyBatis)")
    void getFollowingListTest() throws Exception {
        // given
        // DUMMY DATA: 10번 유저(growth_mindset)는 2, 3, 8번 유저를 팔로우 중 [cite: 81]

        // when & then
        mockMvc.perform(get("/api/v1/follow/following/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3)) // 3명을 팔로우하고 있는지
                .andExpect(jsonPath("$[0].name").value("김개발")) // 2번 유저
                .andExpect(jsonPath("$[1].name").value("이나영")) // 3번 유저
                .andExpect(jsonPath("$[2].name").value("장버그")); // 8번 유저
    }

    @Test
    @DisplayName("[R] 특정 유저의 팔로워 목록을 조회한다 (MyBatis)")
    void getFollowerListTest() throws Exception {
        // given
        // DUMMY DATA: 2번 유저(김개발)는 3, 4, 6, 8, 10, 20번 유저에게 팔로우받는 중 (총 6명) [cite: 81]

        // when & then
        mockMvc.perform(get("/api/v1/follow/followers/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(6)); // 6명인지 확인
    }
}