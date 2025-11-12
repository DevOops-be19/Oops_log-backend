package com.devoops.oopslog.ooh.command.dto;


import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OohCommandCreateDTO {

    // 기존 기록물
    private Long oohId;
    private String oohIsPrivate;
    private String oohTitle;
    private String oohContent;
    private String oohAIAnswer;
    private LocalDateTime oohCreateDate;
    private LocalDateTime oohModifyDate;
    private String oohIsDeleted;
    private Long oohUserId;
    private String name;

    // 태그
    @Size(max = 3, message = "태그는 최대 3개까지만 선택할 수 있습니다.")
    private List<Long> tagIds;

    @Size(max = 3, message = "감정태그는 최대 3개까지만 선택할 수 있습니다.")
    private List<String> emoTagIds;

}


