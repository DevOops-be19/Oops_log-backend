package com.devoops.oopslog.ooh.command.repository;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// JpaRepository<OohCommandEntity, 이부분> 뒤에는 PK가 들어가야함
public interface OohCommandRepository extends JpaRepository<OohCommandEntity, Long> {

    @Query(value = "SELECT DATE(create_date) AS date, COUNT(*) AS count " +
            "FROM ooh_record WHERE user_id = :userId AND is_deleted = 'N' GROUP BY DATE(create_date)",
            nativeQuery = true)
    List<Object[]> countPostsByUserGroupByDate(@Param("userId") Long userId);
}
