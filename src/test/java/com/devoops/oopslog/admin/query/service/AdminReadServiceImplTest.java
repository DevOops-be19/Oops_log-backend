package com.devoops.oopslog.admin.query.service;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import com.devoops.oopslog.admin.query.dto.AllTagDTO;
import com.devoops.oopslog.admin.query.mapper.AdminReadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminReadServiceImplTest {

    private AdminReadMapper adminReadMapper;
    private AdminReadServiceImpl adminReadService;

    @BeforeEach
    void setUp() {
        adminReadMapper = mock(AdminReadMapper.class);
        adminReadService = new AdminReadServiceImpl(adminReadMapper);
    }

    // =========================================================
    @Test
    @DisplayName("전체 회원 목록 조회 - 페이지네이션 계산 검증")
    void testGetAllMemberPagination() {
        int page = 2;
        int size = 10;
        int expectedOffset = 10;  // (page - 1) * size = 10

        List<AllMemberDTO> mockList = Arrays.asList(
                new AllMemberDTO(),
                new AllMemberDTO()
        );

        when(adminReadMapper.selectAllMember(size, expectedOffset))
                .thenReturn(mockList);

        List<AllMemberDTO> result = adminReadService.getAllMember(page, size);

        assertEquals(mockList.size(), result.size());
        verify(adminReadMapper, times(1))
                .selectAllMember(size, expectedOffset);
    }

    // =========================================================
    @Test
    @DisplayName("전체 태그 조회 성공")
    void testGetAllTag() {
        List<AllTagDTO> mockTags = Arrays.asList(
                new AllTagDTO(),
                new AllTagDTO()
        );

        when(adminReadMapper.selectAllTag()).thenReturn(mockTags);

        List<AllTagDTO> result = adminReadService.getAllTag();

        assertEquals(2, result.size());
        verify(adminReadMapper, times(1)).selectAllTag();
    }
}
