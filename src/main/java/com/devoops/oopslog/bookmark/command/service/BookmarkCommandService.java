package com.devoops.oopslog.bookmark.command.service;

import com.devoops.oopslog.bookmark.command.dto.BookmarkRequestDto;
import com.devoops.oopslog.bookmark.command.entity.Bookmark;
import com.devoops.oopslog.bookmark.command.repository.BookmarkCommandRepository;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;

import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkCommandService {

    // CUD용 JPA 리포지토리
    private final BookmarkCommandRepository bookmarkCommandRepository;
    // 의존하는 공통 엔티티 리포지토리
    private final MemberCommandRepository memberCommandRepository;
    private final OohCommandRepository oohCommandRepository;
    private final OopsCommandRepository oopsCommandRepository;

    private static final String TYPE_OOH = "ooh";
    private static final String TYPE_OOPS = "oops";

    /**
     * 북마크 추가 C (JPA)
     */
    @Transactional
    public void addBookmark(BookmarkRequestDto request) {
        Long userId = request.getUserId();
        Long recordId = request.getRecordId();
        String recordType = request.getRecordType();

        log.info("북마크 추가 시도: userId={}, type={}, recordId={}",
                userId, recordType, recordId);

        Member user = memberCommandRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);

        if (TYPE_OOH.equalsIgnoreCase(recordType)) {
            OohCommandEntity ooh = oohCommandRepository.findById(recordId)
                    .orElseThrow(() -> new NoSuchElementException("Ooh 게시글을 찾을 수 없습니다."));
            if (bookmarkCommandRepository.existsByUserAndOohRecord(user, ooh)) {
                throw new IllegalStateException("이미 북마크한 Ooh 게시글입니다.");
            }
            bookmark.setOohRecord(ooh);
        } else if (TYPE_OOPS.equalsIgnoreCase(recordType)) {
            OopsCommandEntity oops = oopsCommandRepository.findById(recordId)
                    .orElseThrow(() -> new NoSuchElementException("Oops 게시글을 찾을 수 없습니다."));
            if (bookmarkCommandRepository.existsByUserAndOopsRecord(user, oops)) {
                throw new IllegalStateException("이미 북마크한 Oops 게시글입니다.");
            }
            bookmark.setOopsRecord(oops);
        } else {
            throw new IllegalArgumentException("유효하지 않은 레코드 타입입니다. 'ooh' 또는 'oops'여야 합니다.");
        }

        bookmarkCommandRepository.save(bookmark);

        log.info("북마크 추가 성공: userId={}, bookmarkId={}", userId, bookmark.getId());
    }

    /**
     * 북마크 삭제 D (JPA)
     */
    @Transactional
    public void removeBookmark(BookmarkRequestDto request) {
        Long userId = request.getUserId();
        Long recordId = request.getRecordId();
        String recordType = request.getRecordType();

        log.info("북마크 삭제 시도: userId={}, type={}, recordId={}",
                userId, recordType, recordId);

        Member user = memberCommandRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        if (TYPE_OOH.equalsIgnoreCase(recordType)) {
            OohCommandEntity ooh = oohCommandRepository.findById(recordId)
                    .orElseThrow(() -> new NoSuchElementException("Ooh 게시글을 찾을 수 없습니다."));
            bookmarkCommandRepository.deleteByUserAndOohRecord(user, ooh);
        } else if (TYPE_OOPS.equalsIgnoreCase(recordType)) {
            OopsCommandEntity oops = oopsCommandRepository.findById(recordId)
                    .orElseThrow(() -> new NoSuchElementException("Oops 게시글을 찾을 수 없습니다."));
            bookmarkCommandRepository.deleteByUserAndOopsRecord(user, oops);
        } else {
            throw new IllegalArgumentException("유효하지 않은 레코드 타입입니다. 'ooh' 또는 'oops'여야 합니다.");
        }

        log.info("북마크 삭제 성공 : userId={}, type={}, recordId={}",
                userId, recordType, recordId);
    }
}
