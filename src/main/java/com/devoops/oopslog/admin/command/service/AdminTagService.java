package com.devoops.oopslog.admin.command.service;

import com.devoops.oopslog.tag.command.entity.Tag;

public interface AdminTagService {
    Tag addTag(String tagName, String tagType);
    void deleteTag(Long tagId);
}
