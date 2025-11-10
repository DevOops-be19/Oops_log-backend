package com.devoops.oopslog.achivement.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class AchivementSummaryDTO {
    private int oopsCount;
    private int oohCount;

    private List<TagCountDTO> topOopsTags;
    private List<TagCountDTO> topOohTags;
}
