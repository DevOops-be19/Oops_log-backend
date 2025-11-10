package com.devoops.oopslog.oops.command.repository;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<OopsCommandEntity, 이부분> 뒤에는 PK가 들어가야함
public interface OopsCommandRepository extends JpaRepository<OopsCommandEntity, Long> {
}
