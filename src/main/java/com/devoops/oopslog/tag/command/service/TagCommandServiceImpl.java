package com.devoops.oopslog.tag.command.service;

import com.devoops.oopslog.tag.command.dto.TagCommandDTO;
import com.devoops.oopslog.tag.command.entity.*;
import com.devoops.oopslog.tag.command.repository.OohTagRepository;
import com.devoops.oopslog.tag.command.repository.OopsTagRepository;
import com.devoops.oopslog.tag.command.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TagCommandServiceImpl implements TagCommandService {
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final OopsTagRepository oopsTagRepository;
    private final OohTagRepository oohTagRepository;

    @Autowired
    public TagCommandServiceImpl(ModelMapper modelMapper,
                                 TagRepository tagRepository,
                                 OopsTagRepository oopsTagRepository,
                                 OohTagRepository oohTagRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.oopsTagRepository = oopsTagRepository;
        this.oohTagRepository = oohTagRepository;
    }

    /* 필기. 게시글로 이동 */
    @Override
    @Transactional
    public void oopsTagUsed(Long tagId, Long oopsId) {
        OopsTagPK oopsTagPK = new OopsTagPK(tagId, oopsId);
        OopsTag oopsTag = new OopsTag();
        oopsTag.setOopsTagPK(oopsTagPK);

        oopsTagRepository.save(oopsTag);
    }

    /* 필기. 게시글로 이동 */
    @Override
    @Transactional
    public void oohTagUsed(Long tagId, Long oohId) {
        OohTagPK oohTagPK = new OohTagPK(tagId, oohId);
        OohTag oohTag = new OohTag();
        oohTag.setOohTagPK(oohTagPK);

        oohTagRepository.save(oohTag);
    }

    @Override
    @Transactional
    public String tagCreate(TagCommandDTO tagDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Tag tag = modelMapper.map(tagDTO, Tag.class);

        tagRepository.save(tag);

        log.info("만들어진 태그: {}", tag);

        return "tag created";
    }

    @Override
    @Transactional
    public String deleteTagById(Long tagId) {
        Tag tag = tagRepository.findById(tagId).get();
        tagRepository.delete(tag);

        return "tag deleted";
    }

    /* 필기. 게시글로 이동 */
    @Override
    public String deleteUsedOohTag(Long tagId, Long oohId) {
        OohTagPK oohTagPK = new OohTagPK(tagId, oohId);
        OohTag oohTag = oohTagRepository.findById(oohTagPK).get();

        oohTagRepository.delete(oohTag);
        return "ooh tag deleted";
    }

    /* 필기. 게시글로 이동 */
    @Override
    public String deleteUsedOopsTag(Long tagId, Long oopsId) {
        OopsTagPK oopsTagPK = new OopsTagPK(tagId, oopsId);
        OopsTag oopsTag = oopsTagRepository.findById(oopsTagPK).get();

        oopsTagRepository.delete(oopsTag);

        return "oops tag deleted";
    }

    @Override
    @Transactional
    public String modifyTagById(Long tagId, TagCommandDTO tagDTO) {
        Tag tag = tagRepository.findById(tagId).get();
        if(tagDTO.getTag_name() != null && !tag.getTagName().equals(tagDTO.getTag_name())) {
            tag.setTagName(tagDTO.getTag_name());
        }
        if(tagDTO.getTag_type() != null && !tag.getTagType().equals(tagDTO.getTag_type())) {
            tag.setTagType(tagDTO.getTag_type());
        }

        tagRepository.save(tag);
        return "tag modified";
    }
}
