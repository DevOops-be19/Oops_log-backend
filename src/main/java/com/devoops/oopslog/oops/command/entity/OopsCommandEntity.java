package com.devoops.oopslog.oops.command.entity;

import com.devoops.oopslog.member.command.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "oops_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OopsCommandEntity {
//     `oops_record`
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
    private Long oopsId;

    @Column(name = "is_private")
    private String oopsIsPrivate;

    @Column(name = "title")
    private String oopsTitle;

    @Column(name = "content")
    private String oopsContent;

    @Column(name = "ai_answer")
    private String oopsAIAnswer;

    @Column(name = "create_date")
    private LocalDateTime oopsCreateDate;

    @Column(name = "modify_date")
    private LocalDateTime oopsModifyDate;

    @Column(name = "is_deleted", nullable = false)
    private String oopsIsDeleted = "N";

    @Column(name = "user_id")
    private Long oopsUserId;

    // Member 필요시 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    // PrePersist LocalDateTime 때문에
    @PrePersist
    public void prePersist() {
        this.oopsCreateDate = LocalDateTime.now();
        this.oopsModifyDate = this.oopsCreateDate;
        if (this.oopsIsDeleted == null) this.oopsIsDeleted = "N";
        if (this.oopsIsPrivate == null) this.oopsIsPrivate = "N";
    }

    @PreUpdate
    public void preUpdate() {
        this.oopsModifyDate = LocalDateTime.now();
    }


}
