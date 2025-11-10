package com.devoops.oopslog.notice.query.service;

import com.devoops.oopslog.notice.command.repository.NoticeCommandRepository;
import com.devoops.oopslog.notice.query.dto.NoticeQueryDTO;
import com.devoops.oopslog.notice.query.mapper.NoticeQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor      // 생성자 자동으로 해주는 어노테이션
public class NoticeQueryService {

    private final NoticeQueryMapper noticeQueryMapper;
    private final NoticeCommandRepository noticeCommandRepository;

    // 공지사항 전체 조회 및 조건 조회
    public List<NoticeQueryDTO> selectNoticeList(String title, String content, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        int limit  = size;
        int offset = (page - 1) * size;

        return noticeQueryMapper.selectNoticeList(title, content, limit, offset);
    }

    public int selectNoticeCount(String title, String content) {
        return noticeQueryMapper.selectNoticeCount(title, content);
    }
}
