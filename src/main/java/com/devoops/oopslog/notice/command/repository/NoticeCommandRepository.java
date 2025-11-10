package com.devoops.oopslog.notice.command.repository;

import com.devoops.oopslog.notice.command.entity.NoticeCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<NoticeCommandEntity, 이부분> 뒤에는 PK가 들어가야함
public interface NoticeCommandRepository extends JpaRepository<NoticeCommandEntity, Long> {
}
