package com.devoops.oopslog.member.command.repository;

import com.devoops.oopslog.member.command.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthCommandRepository extends JpaRepository<UserAuth,Long> {
}
