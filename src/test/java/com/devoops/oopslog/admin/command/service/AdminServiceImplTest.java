package com.devoops.oopslog.admin.command.service;

import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.tag.command.entity.Tag;
import com.devoops.oopslog.tag.command.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    private TagRepository tagRepository;
    private MemberCommandRepository memberRepository;
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        tagRepository = mock(TagRepository.class);
        memberRepository = mock(MemberCommandRepository.class);
        adminService = new AdminServiceImpl(tagRepository, memberRepository);
    }

    // ===========================================================
    @Test
    @DisplayName("태그 등록 성공")
    void testAddTagSuccess() {
        String tagName = "행복";
        String tagType = "emo";

        when(tagRepository.existsByTagNameAndTagType(tagName, tagType)).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tag result = adminService.addTag(tagName, tagType);

        assertNotNull(result);
        assertEquals(tagName, result.getTagName());
        assertEquals(tagType, result.getTagType());
        verify(tagRepository).save(any(Tag.class));
    }

    // ===========================================================
    @Test
    @DisplayName("허용되지 않은 tagType 입력 시 예외 발생")
    void testAddTagInvalidType() {
        String tagName = "음식";
        String invalidType = "invalidType";

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> adminService.addTag(tagName, invalidType));

        assertTrue(ex.getMessage().contains("허용되지 않은 태그 타입"));
        verify(tagRepository, never()).save(any());
    }

    // ===========================================================
    @Test
    @DisplayName("중복 태그 등록 시 예외 발생")
    void testAddTagDuplicate() {
        String tagName = "기쁨";
        String tagType = "emo";

        when(tagRepository.existsByTagNameAndTagType(tagName, tagType)).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> adminService.addTag(tagName, tagType));

        verify(tagRepository, never()).save(any());
    }

    // ===========================================================
    @Test
    @DisplayName("태그 삭제 성공")
    void testDeleteTagSuccess() {
        Long tagId = 100L;

        when(tagRepository.existsById(tagId)).thenReturn(true);

        adminService.deleteTag(tagId);

        verify(tagRepository, times(1)).deleteById(tagId);
    }

    // ===========================================================
    @Test
    @DisplayName("삭제 대상 태그가 없을 경우 예외 발생")
    void testDeleteTagNotFound() {
        Long tagId = 999L;

        when(tagRepository.existsById(tagId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> adminService.deleteTag(tagId));

        verify(tagRepository, never()).deleteById(any());
    }

    // ===========================================================
    @Test
    @DisplayName("회원 상태 변경 성공")
    void testUpdateStateMemberSuccess() {
        Long memberId = 10L;
        Character newState = 'B'; // 예: BAN

        Member member = new Member();
        member.setId(memberId);
        member.setUser_state('A');

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member result = adminService.updateStateMember(memberId, newState);

        assertEquals(newState, result.getUser_state());
        verify(memberRepository).save(member);
    }

    // ===========================================================
    @Test
    @DisplayName("존재하지 않는 회원 상태 변경 시 예외 발생")
    void testUpdateStateMemberNotFound() {
        Long memberId = 999L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> adminService.updateStateMember(memberId, 'S'));

        verify(memberRepository, never()).save(any());
    }
}
