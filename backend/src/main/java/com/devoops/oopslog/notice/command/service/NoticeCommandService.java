package com.devoops.oopslog.notice.command.service;

import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.notice.command.dto.NoticeCommandCreateDTO;
import com.devoops.oopslog.notice.command.dto.NoticeCommandResponseDTO;
import com.devoops.oopslog.notice.command.entity.NoticeCommandEntity;
import com.devoops.oopslog.notice.command.repository.NoticeCommandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCommandService {

    private final NoticeCommandRepository noticeCommandRepository;
    private final MemberCommandRepository memberCommandRepository;


    //insert
    @Transactional
    public NoticeCommandCreateDTO insertNotice(NoticeCommandCreateDTO noticeCommandCreateDTO) {

        // 최소 검증
        if (noticeCommandCreateDTO.getNoticeUserId() == null)
            throw new IllegalArgumentException("작성자 정보가 없습니다(noticeUserId).");
        if (noticeCommandCreateDTO.getNoticeTitle() == null || noticeCommandCreateDTO.getNoticeTitle().isBlank())
            throw new IllegalArgumentException("제목은 필수입니다.");
        if (noticeCommandCreateDTO.getNoticeContent() == null || noticeCommandCreateDTO.getNoticeContent().isBlank())
            throw new IllegalArgumentException("내용은 필수입니다.");

        // 회원 정보 없으면 저장 못하게함
        boolean exists = memberCommandRepository.existsById(noticeCommandCreateDTO.getNoticeUserId());
        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다: " + noticeCommandCreateDTO.getNoticeUserId());
        }

        // 저장할 정보 및 body에 담을 것
        NoticeCommandEntity entity = new NoticeCommandEntity();
        entity.setNoticeUserId(noticeCommandCreateDTO.getNoticeUserId());
        entity.setNoticeTitle(noticeCommandCreateDTO.getNoticeTitle());
        entity.setNoticeContent(noticeCommandCreateDTO.getNoticeContent());

        NoticeCommandEntity saved = noticeCommandRepository.save(entity);

        String name = memberCommandRepository.findById(saved.getNoticeUserId())
                .map(m -> m.getName())
                .orElse(null);

        //응답
        NoticeCommandCreateDTO reqSaved = new NoticeCommandCreateDTO();

        reqSaved.setNoticeId(saved.getNoticeId());
        reqSaved.setNoticeUserId(saved.getNoticeUserId());
        reqSaved.setNoticeTitle(saved.getNoticeTitle());
        reqSaved.setNoticeContent(saved.getNoticeContent());
        reqSaved.setNoticeCreateDate(saved.getNoticeCreateDate());
        reqSaved.setNoticeModifyDate(saved.getNoticeModifyDate());
        reqSaved.setNoticeIsDeleted(saved.getNoticeIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    @Transactional
    public NoticeCommandResponseDTO updateNotice(Long id, NoticeCommandResponseDTO noticeCommandResponseDTO) {
        NoticeCommandEntity notice = noticeCommandRepository.findById(id.longValue())
                .orElseThrow(() ->
                        new EntityNotFoundException("수정할 공지사항을 찾을 수 없습니다. id=" + id));

        // 부분 갱신(보낸 값만 적용 가능)
        if (noticeCommandResponseDTO.getNoticeTitle()
                != null && !noticeCommandResponseDTO.getNoticeTitle().isBlank()) {
            notice.setNoticeTitle(noticeCommandResponseDTO.getNoticeTitle().trim());
        }
        if (noticeCommandResponseDTO.getNoticeContent()
                != null && !noticeCommandResponseDTO.getNoticeContent().isBlank()) {
            notice.setNoticeContent(noticeCommandResponseDTO.getNoticeContent().trim());
        }

        // 수정할 정보
        NoticeCommandEntity saved = noticeCommandRepository.save(notice);

        String name = memberCommandRepository.findById(saved.getNoticeUserId())
                .map(m -> m.getName())
                .orElse(null);

        // 응답 부분
        NoticeCommandResponseDTO reqSaved = new NoticeCommandResponseDTO();
        reqSaved.setNoticeId(saved.getNoticeId());
        reqSaved.setNoticeUserId(saved.getNoticeUserId());
        reqSaved.setNoticeTitle(saved.getNoticeTitle());
        reqSaved.setNoticeContent(saved.getNoticeContent());
        reqSaved.setNoticeCreateDate(saved.getNoticeCreateDate());
        reqSaved.setNoticeModifyDate(saved.getNoticeModifyDate());
        reqSaved.setNoticeIsDeleted(saved.getNoticeIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    // 소프트 delete (is_deleted 에서 Y로 적용)
    @Transactional
    public void deleteNotice(Long id) {
        NoticeCommandEntity notice = noticeCommandRepository.findById(id.longValue())
                .orElseThrow(() -> new EntityNotFoundException("삭제할 기록을 찾을 수 없습니다. id=" + id));
        // 소프트 삭제
        if (!"Y".equalsIgnoreCase(notice.getNoticeIsDeleted())) {
            notice.setNoticeIsDeleted("Y");
            noticeCommandRepository.save(notice); // @PreUpdate로 modify_date 갱신
        }
    }

    // 하드 delete (DB에서 지워버림 관리자 페이지에서 사용할 듯)
    @Transactional
    public void hardDeleteNotice(Long id) {
        if (!noticeCommandRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 기록이 없습니다. id=" + id);
        }
        noticeCommandRepository.deleteById(id);
    }
}
