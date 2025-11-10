package com.devoops.oopslog.admin.command.service;

import com.devoops.oopslog.tag.command.entity.Tag;

public interface TagService {
    Tag addTag(String tagName, String tagType);
    void deleteTag(Long tagId);
}
