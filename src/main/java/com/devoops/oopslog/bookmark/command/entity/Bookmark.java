package com.devoops.oopslog.bookmark.command.entity;

import com.devoops.oopslog.member.command.entity.Member;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oops_id")
    private OopsCommandEntity oopsRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ooh_id")
    private OohCommandEntity oohRecord;
}

