package com.devoops.oopslog.oops.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OopsQueryScrollResponseDTO {
    private List<OopsQueryDTO> oopsList;
    private boolean hasNextPage;
    private int totalCount;  // total 갯수
}