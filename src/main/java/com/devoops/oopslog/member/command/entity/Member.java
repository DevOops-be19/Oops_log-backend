package com.devoops.oopslog.member.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String memberId;
    @Column
    private String memberPw;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String birth;
    @Column
    private Character gender;
    @Column
    private Character user_state;
    @Column
    private String sign_up_date;
}
