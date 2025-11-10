package com.devoops.oopslog.ooh.command.repository;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

// JpaRepository<OohCommandEntity, 이부분> 뒤에는 PK가 들어가야함
public interface OohCommandRepository extends JpaRepository<OohCommandEntity, Long> {

    @Query("SELECT COUNT(o) FROM OohCommandEntity o " +
            "WHERE o.oohUserId = :userId " +
            "AND FUNCTION('DATE', o.oohCreateDate) = :date")
    int countByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
