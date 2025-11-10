package com.devoops.oopslog.tag.query.service;

import com.devoops.oopslog.tag.query.dto.TagNameDTO;

import java.util.List;

public interface TagQueryService {
    List<TagNameDTO> getOopsTagByOopsId(int oopsId);

    List<TagNameDTO> getOohTagByOohId(int oohId);

    List<TagNameDTO> getOopsTag();

    List<TagNameDTO> getOohTag();
}
