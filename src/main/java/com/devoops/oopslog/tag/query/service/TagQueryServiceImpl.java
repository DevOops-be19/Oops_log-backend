package com.devoops.oopslog.tag.query.service;

import com.devoops.oopslog.tag.query.dto.TagNameDTO;
import com.devoops.oopslog.tag.query.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagQueryServiceImpl implements TagQueryService {
    private final TagMapper tagMapper;

    @Autowired
    public TagQueryServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }


    @Override
    public List<TagNameDTO> getOopsTagByOopsId(int oopsId) {
        List<TagNameDTO> tagNameList = tagMapper.selectUsedOopsTagName(oopsId);

        return tagNameList;
    }

    @Override
    public List<TagNameDTO> getOohTagByOohId(int oohId) {
        List<TagNameDTO> tagNameList = tagMapper.selectUsedOohTagName(oohId);

        return tagNameList;
    }

    @Override
    public List<TagNameDTO> getOopsTag() {
        List<TagNameDTO> tagNameList = tagMapper.selectOopsTag();

        return tagNameList;
    }

    @Override
    public List<TagNameDTO> getOohTag() {
        List<TagNameDTO> tagNameList = tagMapper.selectOohTag();

        return tagNameList;
    }
}
