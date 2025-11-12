package com.devoops.oopslog.ooh.command.service;

import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.ooh.command.dto.OohCommandCreateDTO;
import com.devoops.oopslog.ooh.command.dto.OohCommandResponseDTO;
import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.tag.command.entity.OohTag;
import com.devoops.oopslog.tag.command.entity.OohTagPK;
import com.devoops.oopslog.tag.command.entity.OopsTag;
import com.devoops.oopslog.tag.command.entity.OopsTagPK;
import com.devoops.oopslog.tag.command.repository.OohTagRepository;
import com.devoops.oopslog.tag.command.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OohCommandService {

    private final OohCommandRepository oohCommandRepository;
    private final MemberCommandRepository memberCommandRepository;
    private final OohTagRepository oohTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public OohCommandCreateDTO insertOoh(OohCommandCreateDTO oohCommandCreateDTO) {

        // 최소 검증
        if (oohCommandCreateDTO.getOohUserId() == null)
            throw new IllegalArgumentException("작성자 정보가 없습니다(oohUserId).");
        if (oohCommandCreateDTO.getOohTitle() == null || oohCommandCreateDTO.getOohTitle().isBlank())
            throw new IllegalArgumentException("제목은 필수입니다.");
        if (oohCommandCreateDTO.getOohContent() == null || oohCommandCreateDTO.getOohContent().isBlank())
            throw new IllegalArgumentException("내용은 필수입니다.");
        if (oohCommandCreateDTO.getTagIds() != null && oohCommandCreateDTO.getTagIds().size() > 3) {
            throw new IllegalArgumentException("태그는 최대 3개까지만 선택할 수 있습니다.");
        }

        // 회원 정보 없으면 저장 못하게함
        boolean exists = memberCommandRepository.existsById(oohCommandCreateDTO.getOohUserId());
        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다: " + oohCommandCreateDTO.getOohUserId());
        }

        // 저장할 정보 및 body에 담을 것
        OohCommandEntity entity = new OohCommandEntity();
        entity.setOohUserId(oohCommandCreateDTO.getOohUserId());
        entity.setOohTitle(oohCommandCreateDTO.getOohTitle());
        entity.setOohContent(oohCommandCreateDTO.getOohContent());

        entity.setOohIsPrivate(oohCommandCreateDTO.getOohIsPrivate()
                !=null ? oohCommandCreateDTO.getOohIsPrivate() : "N");
        entity.setOohAIAnswer(oohCommandCreateDTO.getOohAIAnswer());
        entity.setOohCreateDate(oohCommandCreateDTO.getOohCreateDate());
        entity.setOohModifyDate(oohCommandCreateDTO.getOohModifyDate());

        OohCommandEntity saved =  oohCommandRepository.save(entity);

        if (oohCommandCreateDTO.getTagIds() != null && !oohCommandCreateDTO.getTagIds().isEmpty()) {
            log.info("tagIds: {}", oohCommandCreateDTO.getTagIds());

            for (Long tagId : oohCommandCreateDTO.getTagIds()) {


                // 복합키 생성
                log.info("생성된 oopsID: {}", saved.getOohId());
                log.info("태그: {}", tagId);
                OohTagPK oohTagPK = new OohTagPK(tagId, saved.getOohId());

                // 관계 엔티티 생성 및 저장
                OohTag oohTag = new OohTag();
                oohTag.setOohTagPK(oohTagPK);

                oohTagRepository.save(oohTag);
            }
        }

        // ===== 감정 태그 저장 (relatedTags: ["우울","슬픔","걱정"]) =====
        if (oohCommandCreateDTO.getEmoTagIds() != null && !oohCommandCreateDTO.getEmoTagIds().isEmpty()) {
            List<String> relatedTags = oohCommandCreateDTO.getEmoTagIds();

            // 1️⃣ 태그 이름 목록으로 id 조회
            List<Long> emoTagIds = tagRepository.findIdsByTagName(relatedTags);
            // 2️⃣ 조회된 태그 id를 ooh_tag에 저장
            for (Long emoTagId : emoTagIds) {
                OohTagPK oohTagPK = new OohTagPK(emoTagId, saved.getOohId());
                OohTag oohTag = new OohTag();
                oohTag.setOohTagPK(oohTagPK);
                oohTagRepository.save(oohTag);
            }
        }

        String name = memberCommandRepository.findById(saved.getOohUserId())
                .map(m -> m.getName())
                .orElse(null);

        // 응답
        OohCommandCreateDTO reqSaved = new OohCommandCreateDTO();

        reqSaved.setOohId(saved.getOohId());
        reqSaved.setOohUserId(saved.getOohUserId());
        reqSaved.setOohTitle(saved.getOohTitle());
        reqSaved.setOohContent(saved.getOohContent());
        reqSaved.setOohIsPrivate(saved.getOohIsPrivate());
        reqSaved.setOohAIAnswer(saved.getOohAIAnswer());
        reqSaved.setOohCreateDate(saved.getOohCreateDate());
        reqSaved.setOohModifyDate(saved.getOohModifyDate());
        reqSaved.setOohIsDeleted(saved.getOohIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    @Transactional
    public OohCommandResponseDTO updateOoh(Long id, OohCommandResponseDTO oohCommandResponseDTO) {
        OohCommandEntity ooh = oohCommandRepository.findById(id.longValue())
                .orElseThrow(() ->
                        new EntityNotFoundException("수정할 기록을 찾을 수 없습니다. id=" + id));

        // 부분 갱신(보낸 값만 적용 가능)
        if (oohCommandResponseDTO.getOohTitle()
                != null && !oohCommandResponseDTO.getOohTitle().isBlank()) {
            ooh.setOohTitle(oohCommandResponseDTO.getOohTitle().trim());
        }
        if (oohCommandResponseDTO.getOohContent()
                != null && !oohCommandResponseDTO.getOohContent().isBlank()) {
            ooh.setOohContent(oohCommandResponseDTO.getOohContent().trim());
        }
        if (oohCommandResponseDTO.getOohIsPrivate()
                != null) {
            ooh.setOohIsPrivate(oohCommandResponseDTO.getOohIsPrivate());
            // "Y"/"N"만 오게 검증하려면 별도 Validator 사용해서 코드 수정하기
        }
        if (oohCommandResponseDTO.getTagIds() != null && oohCommandResponseDTO.getTagIds().size() > 3) {
            throw new IllegalArgumentException("태그는 최대 3개까지만 선택할 수 있습니다.");
        }

        // 수정할 정보
        OohCommandEntity saved = oohCommandRepository.save(ooh);

        // 게시글에 달린 기존 태그들
        List<Long> originalTagIds = oohTagRepository.findTagIdsByOohId(saved.getOohId());

        // 변경될 태그 목록
        List<Long> newTagIds = oohCommandResponseDTO.getTagIds();

        // 추가 및 삭제를 위해 태그 집합 비교
        Set<Long> originalSet = new HashSet<>(originalTagIds);
        Set<Long> newSet = new HashSet<>(newTagIds);

        Set<Long> toAdd = new HashSet<>(newSet);
        toAdd.removeAll(originalSet);

        Set<Long> toDelete = new HashSet<>(originalSet);
        toDelete.removeAll(newSet);

        // 삭제
        for(Long tagId : toDelete){
            OohTagPK oohTagPK = new OohTagPK(tagId, saved.getOohId());
            oohTagRepository.deleteById(oohTagPK);
        }

        // 추가
        for(Long tagId : toAdd){
            OohTagPK pk = new OohTagPK(tagId, saved.getOohId());
            OohTag oohTag = new OohTag();
            oohTag.setOohTagPK(pk);
            oohTagRepository.save(oohTag);
        }



        String name = memberCommandRepository.findById(saved.getOohUserId())
                .map(m -> m.getName())
                .orElse(null);

        // 응답 부분
        OohCommandResponseDTO reqSaved = new OohCommandResponseDTO();
        reqSaved.setOohId(saved.getOohId());
        reqSaved.setOohUserId(saved.getOohUserId());
        reqSaved.setOohTitle(saved.getOohTitle());
        reqSaved.setOohContent(saved.getOohContent());
        reqSaved.setOohIsPrivate(saved.getOohIsPrivate());
        reqSaved.setOohAIAnswer(saved.getOohAIAnswer());
        reqSaved.setOohCreateDate(saved.getOohCreateDate());
        reqSaved.setOohModifyDate(saved.getOohModifyDate());  // 수정일 이거 업데이트됨
        reqSaved.setOohIsDeleted(saved.getOohIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    // 소프트 delete (is_deleted 에서 Y로 적용)
    @Transactional
    public void deleteOoh(Long id) {
        OohCommandEntity ooh = oohCommandRepository.findById(id.longValue())
                    .orElseThrow(() -> new EntityNotFoundException("삭제할 기록을 찾을 수 없습니다. id=" + id));

            // 소프트 삭제
            if (!"Y".equalsIgnoreCase(ooh.getOohIsDeleted())) {
                ooh.setOohIsDeleted("Y");
                oohCommandRepository.save(ooh); // @PreUpdate로 modify_date 갱신
            }
        }
    // 하드 delete (DB에서 지워버림 관리자 페이지에서 사용할 듯)
    @Transactional
    public void hardDeleteOoh(Long id) {
        if (!oohCommandRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 기록이 없습니다. id=" + id);
        }
        oohCommandRepository.deleteById(id);
    }
}

