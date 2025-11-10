package com.devoops.oopslog.notice.command.entity;

import com.devoops.oopslog.member.command.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoticeCommandEntity {

//    id, title, content, create_date, modify_date, is_deleted, user_id(FK)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long noticeId;

    @Column(name = "title")
    private String noticeTitle;

    @Column(name = "content")
    private String noticeContent;

    @Column(name = "create_date")
    private LocalDateTime noticeCreateDate;

    @Column(name = "modify_date")
    private LocalDateTime noticeModifyDate;

    @Column(name = "is_deleted")
    private String noticeIsDeleted;

    @Column(name = "user_id")
    private Long noticeUserId;

    // Member 필요시 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    // PrePersist LocalDateTime 때문에
    @PrePersist
    public void prePersist() {
        this.noticeCreateDate = LocalDateTime.now();
        this.noticeModifyDate = this.noticeCreateDate;
        if (this.noticeIsDeleted == null) this.noticeIsDeleted = "N";
    }

    @PreUpdate
    public void preUpdate() {
        this.noticeModifyDate = LocalDateTime.now();
    }
}
