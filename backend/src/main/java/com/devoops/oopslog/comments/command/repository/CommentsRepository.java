package com.devoops.oopslog.comments.command.repository;

import com.devoops.oopslog.comments.command.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
