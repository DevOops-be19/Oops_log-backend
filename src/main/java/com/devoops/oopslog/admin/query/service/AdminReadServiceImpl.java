package com.devoops.oopslog.admin.query.service;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import com.devoops.oopslog.admin.query.dto.AllTagDTO;
import com.devoops.oopslog.admin.query.mapper.AdminReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReadServiceImpl implements AdminReadService {

    private AdminReadMapper adminReadMapper;

    @Autowired
    public AdminReadServiceImpl(AdminReadMapper adminReadMapper) {
        this.adminReadMapper = adminReadMapper;
    }


    @Override
    public List<AllMemberDTO> getAllMember(int page, int size) {
        int offset = (page - 1) * size;
        return adminReadMapper.selectAllMember(size, offset);
    }

    @Override
    public List<AllTagDTO> getAllTag() {
        return adminReadMapper.selectAllTag();
    }
}
