package com.devoops.oopslog.tag.command.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TagCommandDTO {
    private Long id;
    private String tag_name;
    private String tag_type;
}
