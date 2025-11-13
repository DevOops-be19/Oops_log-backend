package com.devoops.oopslog.ooh.query.service;

import com.devoops.oopslog.ooh.query.dto.OohMemIdDTO;
import com.devoops.oopslog.ooh.query.dto.OohQueryDTO;
import com.devoops.oopslog.ooh.query.dto.OohQuerySelectDTO;
import com.devoops.oopslog.ooh.query.mapper.OohQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor      // 생성자 자동으로 해주는 어노테이션
public class OohQueryService {

    private final OohQueryMapper oohQueryMapper;


    // 공지사항 전체 조회 및 조건 조회
    public List<OohQueryDTO> selectOohList(String title, String content, String name, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        int limit  = size;
        int offset = (page - 1) * size;

        return oohQueryMapper.selectOohList(title, content, name, limit, offset);
    }

    public int selectOohCount(String title, String name, String content) {
        return oohQueryMapper.selectOohCount(title, name, content);
    }


    public OohQuerySelectDTO selectOohById(Long oohId) {
        OohQuerySelectDTO oohRecord = oohQueryMapper.selectOohRecordByOohId(oohId);

        return oohRecord;
    }

    public List<OohMemIdDTO> selectOohRecordByMemId(Long id){
        return oohQueryMapper.selectOohRecordByMemId(id);
    }
}
