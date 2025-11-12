package com.devoops.oopslog.likes.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LikesDTO {
    private Long id;
    private Long oopsId;
    private Long user_id;
    private Long oohId;
}
