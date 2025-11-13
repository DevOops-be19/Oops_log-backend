package com.devoops.oopslog.achivement.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class AchivementSummaryDTO {
    private List<OopsRecordCountDTO> OopsRecords;
    private List<OohRecordCountDTO> OohRecords;
    private List<TagCountDTO> topOopsTags;
    private List<TagCountDTO> topOohTags;
}
