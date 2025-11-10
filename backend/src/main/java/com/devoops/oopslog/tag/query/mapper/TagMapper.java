package com.devoops.oopslog.tag.query.mapper;

import com.devoops.oopslog.tag.query.dto.TagNameDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<TagNameDTO> selectUsedOopsTagName(int oopsId);

    List<TagNameDTO> selectUsedOohTagName(int oohId);

    List<TagNameDTO> selectOopsTag();

    List<TagNameDTO> selectOohTag();
}
