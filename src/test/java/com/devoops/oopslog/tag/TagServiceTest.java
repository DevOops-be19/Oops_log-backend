package com.devoops.oopslog.tag;

import com.devoops.oopslog.tag.command.dto.TagCommandDTO;
import com.devoops.oopslog.tag.command.service.TagCommandService;
import com.devoops.oopslog.tag.query.dto.TagNameDTO;
import com.devoops.oopslog.tag.query.service.TagQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class TagServiceTest {
    private TagQueryService tagQueryService;
    private TagCommandService tagCommandService;

    @Autowired
    public TagServiceTest(TagQueryService tagQueryService, TagCommandService tagCommandService) {
        this.tagQueryService = tagQueryService;
        this.tagCommandService = tagCommandService;
    }

    @DisplayName("태그 조회 테스트 - 하나의 ooh기록에 사용된 태그 조회")
    @Test
    void tagListReadServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<TagNameDTO> tagNameList = tagQueryService.getOohTagByOohId(10);
                    tagNameList.forEach(System.out::println);
                }
        );
    }

    @DisplayName("태그 생성 테스트")
    @Test
    void tagInsertServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    TagCommandDTO tag = new TagCommandDTO(null, "태그이름", "ooh");
                    String isTagInserted = tagCommandService.tagCreate(tag);
                    System.out.println("태그 생성 여부: " + isTagInserted);
                }
        );
    }

    @DisplayName("태그 삭제 테스트")
    @Test
    void tagDeleteServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    String isTagDeleted = tagCommandService.deleteTagById((long)10);
                    System.out.println("태그 삭제 여부: " + isTagDeleted);
                }
        );
    }
}
