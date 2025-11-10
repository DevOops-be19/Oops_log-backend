package com.devoops.oopslog.oops.command.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OopsCommandCreateDTO {

    // 기존 기록물
    private Long oopsId;
    private Long oopsUserId;
    private String oopsIsPrivate;
    private String oopsTitle;
    private String oopsContent;
    private String oopsAIAnswer;
    private LocalDateTime oopsCreateDate;
    private LocalDateTime oopsModifyDate;
    private String oopsIsDeleted;
    private String name;

    // 태그
    @Size(max = 3, message = "태그는 최대 3개까지만 선택할 수 있습니다.")
    private List<Long> tagIds;



}
