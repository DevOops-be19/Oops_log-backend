package com.devoops.oopslog.ooh.query.dto;

import com.devoops.oopslog.notice.query.dto.NoticeQueryDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OohQueryScrollResponseDTO {
    private List<OohQueryDTO> oohList;
    private boolean hasNextPage;
    private int totalCount;  // total 갯수
}