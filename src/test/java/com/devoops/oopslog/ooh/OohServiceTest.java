package com.devoops.oopslog.ooh;

import com.devoops.oopslog.ooh.command.dto.OohCommandCreateDTO;
import com.devoops.oopslog.ooh.command.service.OohCommandService;
import com.devoops.oopslog.ooh.query.dto.OohQueryDTO;
import com.devoops.oopslog.ooh.query.service.OohQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class OohServiceTest {
    private OohQueryService oohQueryService;
    private OohCommandService oohCommandService;

    public OohServiceTest(OohQueryService oohQueryService, OohCommandService oohCommandService) {
        this.oohQueryService = oohQueryService;
        this.oohCommandService = oohCommandService;
    }

    @DisplayName("Ooh기록 목록 조회 테스트")
    @Test
    void OohReadServiceTest() {
        List<OohQueryDTO> oohRecordList =
                oohQueryService.selectOohList("제목", "내용", "이름", 1, 10);
        oohRecordList.forEach(System.out::println);
    }

}
