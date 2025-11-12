package com.devoops.oopslog.oops.command.repository;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository<OopsCommandEntity, 이부분> 뒤에는 PK가 들어가야함
public interface OopsCommandRepository extends JpaRepository<OopsCommandEntity, Long> {
    @Query(value = "SELECT DATE(create_date) AS date, COUNT(*) AS count " +
            "FROM oops_record WHERE user_id = :userId AND is_deleted = 'N' GROUP BY DATE(create_date)",
            nativeQuery = true)
    List<Object[]> countPostsByUserGroupByDate(@Param("userId") Long userId);
}
