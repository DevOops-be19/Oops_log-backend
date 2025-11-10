package com.devoops.oopslog.member.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="login_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login_success_date;
    @Column
    private String login_ip;
    @Column
    private Character login_is_succeed;
    @Column
    private Long user_id;
}
