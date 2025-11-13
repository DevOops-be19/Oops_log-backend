package com.devoops.oopslog.achivement.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class AchivementInfoDTO {
    private int oopsCount;
    private int oohCount;
    private List<FindYearMonthDTO> findYearOops;
    private List<FindYearMonthDTO> findYearOoh;

}
