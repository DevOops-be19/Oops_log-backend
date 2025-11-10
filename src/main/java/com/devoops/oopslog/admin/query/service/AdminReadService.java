package com.devoops.oopslog.admin.query.service;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import com.devoops.oopslog.admin.query.dto.AllTagDTO;

import java.util.List;

public interface AdminReadService {
    List<AllMemberDTO> getAllMember(int page, int size);
    List<AllTagDTO> getAllTag();
}
