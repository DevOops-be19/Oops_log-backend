package com.devoops.oopslog.oops.query.service;

import com.devoops.oopslog.oops.query.dto.OopsQueryDTO;
import com.devoops.oopslog.oops.query.dto.OopsQuerySelectDTO;
import com.devoops.oopslog.oops.query.mapper.OopsQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor      // 생성자 자동으로 해주는 어노테이션
public class OopsQueryService {

    private final OopsQueryMapper oopsQueryMapper;


    public List<OopsQueryDTO> selectOopsList(String title, String content, String name, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        int limit  = size;
        int offset = (page - 1) * size;

        return oopsQueryMapper.selectOopsList(title, content, name, limit, offset);
    }

    public int selectOopsCount(String title, String name, String content) {
        return oopsQueryMapper.selectOopsCount(title, name, content);
    }

    public OopsQuerySelectDTO selectOopsById(Long oopsId) {
        OopsQuerySelectDTO oopsRecord = oopsQueryMapper.selectOopsRecordByOopsId(oopsId);

        return oopsRecord;
    }
}
