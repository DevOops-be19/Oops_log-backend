package com.devoops.oopslog.oops;

import com.devoops.oopslog.oops.command.dto.OopsCommandCreateDTO;
import com.devoops.oopslog.oops.command.service.OopsCommandService;
import com.devoops.oopslog.oops.query.dto.OopsQueryDTO;
import com.devoops.oopslog.oops.query.dto.OopsQuerySelectDTO;
import com.devoops.oopslog.oops.query.service.OopsQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class OopsServiceTest {
    private OopsQueryService oopsQueryService;
    private OopsCommandService oopsCommandService;

    @Autowired
    public OopsServiceTest(OopsQueryService oopsQueryService, OopsCommandService oopsCommandService) {
        this.oopsQueryService = oopsQueryService;
        this.oopsCommandService = oopsCommandService;
    }

    @DisplayName("Oops기록 목록 조회 테스트")
    @Test
    void OopsReadServiceTest() {
        List<OopsQueryDTO> oopsRecordList = oopsQueryService.selectOopsList("제목", "내용", "이름", 1, 10);
        oopsRecordList.forEach(System.out::println);
    }

    @DisplayName("Oops기록 작성 테스트")
    @Test
    void OopsInsertServiceTest() {
        OopsCommandCreateDTO oopsInsertRecord =
                new OopsCommandCreateDTO(
                        null, // oopsId
                        48L, // oopsUserId
                        "N", // oopsIsPrivate
                        "title", // oopsTitle
                        "comment", // oopsContent
                        null, // oopsAIAnswer
                        LocalDateTime.now(), // oopsCreateDate
                        null, // oopsModifyDate
                        "N", // oopsIsDeleted
                        "name", // name
                        Arrays.asList(22L, 23L), // tagIds
                        Arrays.asList("만족", "감사"));
        OopsCommandCreateDTO oopsRecord = oopsCommandService.insertOops(oopsInsertRecord);
        System.out.println("생성된 oops기록: " + oopsRecord);

    }

    @DisplayName("oops기록 삭제 테스트")
    @Test
    void deleteOopsServiceTest() {
        oopsCommandService.deleteOops(10L);
    }
}
