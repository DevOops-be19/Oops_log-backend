package com.devoops.oopslog.bookmark.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkRequestDto {
    private Long userId;       // 북마크를 실행하는 유저 ID
    private String recordType; // "ooh" 또는 "oops"
    private Long recordId;   // Ooh ID 또는 Oops ID
}