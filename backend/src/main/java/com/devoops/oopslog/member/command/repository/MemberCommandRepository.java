package com.devoops.oopslog.member.command.repository;

import com.devoops.oopslog.member.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommandRepository extends JpaRepository<Member,Long> {

    Member findByMemberId(String memberId);

    Member findByEmail(String email);
}