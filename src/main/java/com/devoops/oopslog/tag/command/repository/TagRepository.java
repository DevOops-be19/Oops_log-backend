package com.devoops.oopslog.tag.command.repository;

import com.devoops.oopslog.tag.command.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByTagNameAndTagType(String tagName, String tagType);

    @Query("SELECT t.id FROM Tag t WHERE t.tagName IN :names")
    List<Long> findIdsByTagName(@Param("names") List<String> names);
}
