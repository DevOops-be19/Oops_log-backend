package com.devoops.oopslog.oops.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OopsDetailDTO {

    private Long id;
    private String type;
    private String title;
    private String content;
    private String isPrivate;
    private String aiAnswer;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private Long userId;
    private String userName;

    private List<String> tags;       // 태그명
    private List<String> emotions;   // 감정태그명
    private long likesCount;
    private boolean likedByMe;       // 당분간 false 고정

    private List<OopsDetailCommentDTO> comments;
    private long commentsTotal;
}
