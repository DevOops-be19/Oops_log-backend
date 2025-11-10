package com.devoops.oopslog.tag.command.repository;

import com.devoops.oopslog.tag.command.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByTagNameAndTagType(String tagName, String tagType);
}
