//package com.devoops.oopslog.bookmark;
//
//import com.devoops.oopslog.bookmark.command.dto.BookmarkRequestDto;
//import com.devoops.oopslog.bookmark.command.entity.Bookmark;
//import com.devoops.oopslog.bookmark.command.repository.BookmarkCommandRepository;
//import com.devoops.oopslog.bookmark.command.service.BookmarkCommandService;
//import com.devoops.oopslog.bookmark.query.dto.BookmarkItemDto;
//import com.devoops.oopslog.bookmark.query.service.BookmarkQueryService;
//import com.devoops.oopslog.member.command.entity.Member;
//import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
//import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
//import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
//import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
//import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Rollback // 테스트 후 DB 롤백 (데이터 오염 방지)
//public class BookmarkServiceTest {
//
//    @Autowired
//    private BookmarkCommandService bookmarkCommandService;
//
//    @Autowired
//    private BookmarkQueryService bookmarkQueryService;
//
//    // --- CUD 및 데이터 검증을 위한 의존성 ---
//    @Autowired
//    private BookmarkCommandRepository bookmarkCommandRepository;
//
//    @Autowired
//    private MemberCommandRepository memberCommandRepository;
//
//    @Autowired
//    private OohCommandRepository oohCommandRepository;
//
//    @Autowired
//    private OopsCommandRepository oopsCommandRepository;
//
//    // 테스트용 더미 데이터
//    private Member testUser1;
//    private Member testUser2;
//    private OohCommandEntity testOoh1;
//    private OopsCommandEntity testOops1;
//
//    @BeforeEach
//    void setUp() {
//
//        // 테스트 격리를 위해 매번 bookmark 테이블을 깨끗하게 비움
//        bookmarkCommandRepository.deleteAllInBatch();
//
//        // CUD 테스트를 위한 기본 데이터 생성 (ID 1, 2가 이미 존재한다고 가정)
//        testUser1 = memberCommandRepository.findById(1L).orElse(null);
//        testUser2 = memberCommandRepository.findById(2L).orElse(null);
//        testOoh1 = oohCommandRepository.findById(1L).orElse(null);
//        testOops1 = oopsCommandRepository.findById(1L).orElse(null);
//
//        // 더미 데이터가 없을 경우를 대비한 방어 코드 (테스트 환경에 따라 필요)
//        if (testUser1 == null) {
//            testUser1 = new Member();
//            testUser1.setMemberId("testUser1");
//            testUser1.setMemberPw("password");
//            testUser1.setName("테스트유저1"); // "관리자"
//            testUser1 = memberCommandRepository.save(testUser1);
//        }
//        if (testUser2 == null) {
//            testUser2 = new Member();
//            testUser2.setMemberId("testUser2");
//            testUser2.setMemberPw("password");
//            testUser2.setName("테스트유저2"); // "김개발"
//            testUser2 = memberCommandRepository.save(testUser2);
//        }
//        if (testOoh1 == null) {
//            testOoh1 = new OohCommandEntity();
//            testOoh1.setOohTitle("Ooh 테스트 제목");
//            testOoh1.setOohContent("Ooh 테스트 내용");
//            testOoh1.setOohUserId(testUser2.getId()); // 작성자
//            testOoh1 = oohCommandRepository.save(testOoh1);
//        }
//        if (testOops1 == null) {
//            testOops1 = new OopsCommandEntity();
//            testOops1.setOopsTitle("Oops 테스트 제목");
//            testOops1.setOopsContent("Oops 테스트 내용");
//            testOops1.setOopsUserId(testUser2.getId()); // 작성자
//            testOops1 = oopsCommandRepository.save(testOops1);
//        }
//    }
//
//    // --- Command (CUD) 테스트 ---
//
//    @Test
//    @DisplayName("[C] Ooh 게시글 북마크 추가 테스트")
//    void testAddOohBookmark() {
//        // given
//        BookmarkRequestDto requestDto = new BookmarkRequestDto();
//        requestDto.setUserId(testUser1.getId());
//        requestDto.setRecordType("ooh");
//        requestDto.setRecordId(testOoh1.getOohId());
//
//        long countBefore = bookmarkCommandRepository.count();
//
//        // when
//        Assertions.assertDoesNotThrow(
//                () -> bookmarkCommandService.addBookmark(requestDto)
//        );
//
//        // then
//        long countAfter = bookmarkCommandRepository.count();
//        assertThat(countAfter).isEqualTo(countBefore + 1);
//        boolean isBookmarked = bookmarkCommandRepository.existsByUserAndOohRecord(testUser1, testOoh1);
//        Assertions.assertTrue(isBookmarked);
//    }
//
//    @Test
//    @DisplayName("[C] Oops 게시글 북마크 추가 테스트")
//    void testAddOopsBookmark() {
//        // given
//        BookmarkRequestDto requestDto = new BookmarkRequestDto();
//        requestDto.setUserId(testUser1.getId());
//        requestDto.setRecordType("oops");
//        requestDto.setRecordId(testOops1.getOopsId());
//
//        long countBefore = bookmarkCommandRepository.count();
//
//        // when
//        Assertions.assertDoesNotThrow(
//                () -> bookmarkCommandService.addBookmark(requestDto)
//        );
//
//        // then
//        long countAfter = bookmarkCommandRepository.count();
//        assertThat(countAfter).isEqualTo(countBefore + 1);
//        boolean isBookmarked = bookmarkCommandRepository.existsByUserAndOopsRecord(testUser1, testOops1);
//        Assertions.assertTrue(isBookmarked);
//    }
//
//    @Test
//    @DisplayName("[D] Ooh 북마크 삭제 테스트")
//    void testRemoveOohBookmark() {
//        // given: 유저 2가 Ooh 1번 게시글을 미리 북마크
//        Bookmark bookmark = new Bookmark();
//        bookmark.setUser(testUser2);
//        bookmark.setOohRecord(testOoh1);
//        bookmarkCommandRepository.save(bookmark);
//
//        long countBefore = bookmarkCommandRepository.count();
//
//        BookmarkRequestDto requestDto = new BookmarkRequestDto();
//        requestDto.setUserId(testUser2.getId());
//        requestDto.setRecordType("ooh");
//        requestDto.setRecordId(testOoh1.getOohId());
//
//        // when
//        Assertions.assertDoesNotThrow(
//                () -> bookmarkCommandService.removeBookmark(requestDto)
//        );
//
//        // then
//        long countAfter = bookmarkCommandRepository.count();
//        assertThat(countAfter).isEqualTo(countBefore - 1);
//        boolean isBookmarked = bookmarkCommandRepository.existsByUserAndOohRecord(testUser2, testOoh1);
//        Assertions.assertFalse(isBookmarked);
//    }
//
//    @Test
//    @DisplayName("[D] Oops 북마크 삭제 테스트")
//    void testRemoveOopsBookmark() {
//        // given: 유저 2가 Oops 1번 게시글을 미리 북마크
//        Bookmark bookmark = new Bookmark();
//        bookmark.setUser(testUser2);
//        bookmark.setOopsRecord(testOops1);
//        bookmarkCommandRepository.save(bookmark);
//
//        long countBefore = bookmarkCommandRepository.count();
//
//        BookmarkRequestDto requestDto = new BookmarkRequestDto();
//        requestDto.setUserId(testUser2.getId());
//        requestDto.setRecordType("oops");
//        requestDto.setRecordId(testOops1.getOopsId());
//
//        // when
//        Assertions.assertDoesNotThrow(
//                () -> bookmarkCommandService.removeBookmark(requestDto)
//        );
//
//        // then
//        long countAfter = bookmarkCommandRepository.count();
//        assertThat(countAfter).isEqualTo(countBefore - 1);
//        boolean isBookmarked = bookmarkCommandRepository.existsByUserAndOopsRecord(testUser2, testOops1);
//        Assertions.assertFalse(isBookmarked);
//    }
//
//    // --- Query (R) 테스트 ---
//
//    @Test
//    @DisplayName("[R] 특정 유저의 북마크 목록 조회 테스트")
//    void testGetBookmarkList() {
//        // given: 유저 1이 Ooh 1과 Oops 1을 북마크
//        Bookmark b1 = new Bookmark();
//        b1.setUser(testUser1);
//        b1.setOohRecord(testOoh1);
//        bookmarkCommandRepository.save(b1);
//
//        Bookmark b2 = new Bookmark();
//        b2.setUser(testUser1);
//        b2.setOopsRecord(testOops1);
//        bookmarkCommandRepository.save(b2);
//
//        // when
//        List<BookmarkItemDto> bookmarks = Assertions.assertDoesNotThrow(
//                () -> bookmarkQueryService.getBookmarks(testUser1.getId())
//        );
//
//        // then
//        Assertions.assertNotNull(bookmarks);
//        assertThat(bookmarks.size()).isEqualTo(2);
//
//        // Mapper 쿼리 정렬(bookmarkId DESC)에 따라 b2(Oops)가 먼저 나와야 함
//        Assertions.assertEquals("oops", bookmarks.get(0).getRecordType());
//        Assertions.assertEquals(testOops1.getOopsId(), bookmarks.get(0).getRecordId());
//        Assertions.assertEquals(testUser2.getName(), bookmarks.get(0).getAuthorName()); // Oops1 작성자 ("김개발")
//
//        // b1 (Ooh)이 나중에 나와야 함
//        Assertions.assertEquals("ooh", bookmarks.get(1).getRecordType());
//        Assertions.assertEquals(testOoh1.getOohId(), bookmarks.get(1).getRecordId());
//
//        // Ooh1의 작성자가 DB에 "김개발"(testUser2)로 되어 있으므로 "관리자"(testUser1) 대신 testUser2.getName()을 기대
//        Assertions.assertEquals(testUser2.getName(), bookmarks.get(1).getAuthorName());
//    }
//
//    @Test
//    @DisplayName("[C] 존재하지 않는 유저로 북마크 추가 시도")
//    void testAddBookmarkWithInvalidUser() {
//        // given
//        BookmarkRequestDto requestDto = new BookmarkRequestDto();
//        requestDto.setUserId(9999L); // 존재하지 않는 유저 ID
//        requestDto.setRecordType("ooh");
//        requestDto.setRecordId(testOoh1.getOohId());
//
//        // when & then
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            bookmarkCommandService.addBookmark(requestDto);
//        }, "사용자를 찾을 수 없습니다.");
//    }
//}