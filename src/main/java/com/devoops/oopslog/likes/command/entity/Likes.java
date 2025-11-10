package com.devoops.oopslog.likes.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Likes {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oops_id")
    private Long oopsId;

    @Column(name = "ooh_id")
    private Long oohId;

    @Column(name = "user_id")
    private Long user_id;
}
