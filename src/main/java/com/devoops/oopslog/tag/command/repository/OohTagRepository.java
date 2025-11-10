package com.devoops.oopslog.tag.command.repository;

import com.devoops.oopslog.tag.command.entity.OohTag;
import com.devoops.oopslog.tag.command.entity.OohTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OohTagRepository extends JpaRepository<OohTag, OohTagPK> {

    @Query("select o.oohTagPK.tag_id from OohTag o where o.oohTagPK.ooh_id = :oohId")
    List<Long> findTagIdsByOohId(@Param("oohId") Long oohId);
}
