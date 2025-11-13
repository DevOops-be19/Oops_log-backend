package com.devoops.oopslog.admin.command.service;

import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.tag.command.entity.Tag;

public interface AdminService {
    Tag addTag(String tagName, String tagType);
    void deleteTag(Long tagId);
    Member updateStateMember(Long id, Character state);
}
