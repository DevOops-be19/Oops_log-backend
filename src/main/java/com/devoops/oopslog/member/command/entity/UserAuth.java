package com.devoops.oopslog.member.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="auth_id")
    private Long authId;

    public UserAuth(Long userId,Long authId) {
        this.userId = userId;
        this.authId = authId;
    }
}
