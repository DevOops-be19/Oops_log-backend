package com.devoops.oopslog.admin.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRequestDTO {
    private String tagName;
    private String tagType;
}
