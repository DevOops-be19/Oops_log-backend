package com.devoops.oopslog.recordhistory.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "record_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecordHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_date")
    private LocalDateTime countDate;

    @Column(name = "record_type")
    private String recordType;

    @Column(name = "record_count")
    private int recordCount;

    @Column(name = "user_id")
    private Long userId;
}
