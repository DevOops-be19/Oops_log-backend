package com.devoops.oopslog.tag.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OohTagPK {

    @Column(name = "tag_id")
    private Long tag_id;

    @Column(name = "ooh_id")
    private Long ooh_id;
}
