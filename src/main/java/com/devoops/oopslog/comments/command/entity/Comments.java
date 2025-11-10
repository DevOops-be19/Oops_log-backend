package com.devoops.oopslog.comments.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Comments {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Column(name = "is_deleted")
    private String is_deleted;

    @Column(name = "oops_id")
    private Long oops_id;

    @Column(name = "ooh_id")
    private Long ooh_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "notice_id")
    private Long notice_id;
}
