// com.devoops.oopslog.ooh.query.dto.OohDetailDTO
package com.devoops.oopslog.ooh.query.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OohDetailDTO {

    private Long id;
    private String type; // "ooh" 고정
    private String title;
    private String content;
    private String isPrivate;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private Long userId;
    private String userName;

    private List<String> tags;       // 태그명
    private List<String> emotions;   // 감정태그명
    private long likesCount;
    private boolean likedByMe;       // 당분간 false 고정

    private List<OohDetailCommentDTO> comments;
    private long commentsTotal;
}
