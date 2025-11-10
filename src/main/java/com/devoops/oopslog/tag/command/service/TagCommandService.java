package com.devoops.oopslog.tag.command.service;

import com.devoops.oopslog.tag.command.dto.TagCommandDTO;

public interface TagCommandService {
    void oopsTagUsed(Long tagId, Long oopsId);

    void oohTagUsed(Long tagId, Long oohId);

    String tagCreate(TagCommandDTO tagDTO);

    String deleteTagById(Long tagId);

    String deleteUsedOohTag(Long tagId, Long oohId);

    String deleteUsedOopsTag(Long tagId, Long oopsId);

    String modifyTagById(Long tagId, TagCommandDTO tagDTO);
}
