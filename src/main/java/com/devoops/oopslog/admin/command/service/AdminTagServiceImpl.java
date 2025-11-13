package com.devoops.oopslog.admin.command.service;

import com.devoops.oopslog.tag.command.entity.Tag;
import com.devoops.oopslog.tag.command.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminTagServiceImpl implements AdminTagService {
    private final TagRepository tagRepository;

    // 허용 가능한 태그 타입 목록
    private static final List<String> ALLOWED_TAG_TYPES = List.of("ooh", "oops", "emo");

    @Autowired
    public AdminTagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag addTag(String tagName, String tagType) {

        // tagType 검증
        if (!ALLOWED_TAG_TYPES.contains(tagType.toLowerCase())) {
            throw new IllegalArgumentException(
                    String.format("허용되지 않은 태그 타입입니다. (입력된 tagType: %s, 허용된 타입: %s)",
                            tagType, ALLOWED_TAG_TYPES)
            );
        }

        // 중복 확인
        boolean exists = tagRepository.existsByTagNameAndTagType(tagName, tagType);
        if (exists) {
            throw new IllegalArgumentException(
                    String.format("이미 존재하는 태그입니다. (tagName: %s, tagType: %s)", tagName, tagType)
            );
        }

        // 등록
        Tag tag = Tag.builder()
                .tagName(tagName)
                .tagType(tagType)
                .build();
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
        } else {
            throw new IllegalArgumentException("해당 ID의 태그가 존재하지 않습니다: " + tagId);
        }
    }
}
