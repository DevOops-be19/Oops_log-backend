package com.devoops.oopslog.ooh.command.entity;

import com.devoops.oopslog.member.command.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "ooh_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OohCommandEntity {
//     `ooh_record`
//     (`id`,
//     `is_private`,
//     `title`,
//     `content`,
//     `ai_answer`,
//     `create_date`,
//     `modify_date`,
//     `is_deleted`,
//     `user_id`

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long oohId;

    @Column(name = "is_private")
    private String oohIsPrivate;

    @Column(name = "title", nullable = false)
    private String oohTitle;

    @Column(name = "content", nullable = false)
    private String oohContent;

    @Column(name = "ai_answer")
    private String oohAIAnswer;

    @Column(name = "create_date")
    private LocalDateTime oohCreateDate;

    @Column(name = "modify_date")
    private LocalDateTime oohModifyDate;

    @Column(name = "is_deleted", nullable = false)
    private String oohIsDeleted = "N";

    @Column(name = "user_id", nullable = false)
    private Long oohUserId;

    // Member 필요시 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    // PrePersist LocalDateTime 때문에
    @PrePersist
    public void prePersist() {
        this.oohCreateDate = LocalDateTime.now();
        this.oohModifyDate = this.oohCreateDate;
        if (this.oohIsDeleted == null) this.oohIsDeleted = "N";
        if (this.oohIsPrivate == null) this.oohIsPrivate = "N";
    }

    @PreUpdate
    public void preUpdate() {
        this.oohModifyDate = LocalDateTime.now();
    }

}
