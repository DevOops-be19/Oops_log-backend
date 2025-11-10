package com.devoops.oopslog.tag.command.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ooh_tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OohTag {

    @EmbeddedId
    private OohTagPK oohTagPK;
}
