package com.devoops.oopslog.tag.command.repository;

import com.devoops.oopslog.tag.command.entity.OopsTag;
import com.devoops.oopslog.tag.command.entity.OopsTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OopsTagRepository extends JpaRepository<OopsTag, OopsTagPK> {

    @Query("select o.oopsTagPK.tag_id from OopsTag o where o.oopsTagPK.oops_id = :oopsId")
    List<Long> findTagIdsByOopsId(@Param("oopsId") Long oopsId);
}
