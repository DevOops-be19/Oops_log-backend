package com.devoops.oopslog.oops.command.service;

import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.oops.command.dto.OopsCommandCreateDTO;
import com.devoops.oopslog.oops.command.dto.OopsCommandResponseDTO;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import com.devoops.oopslog.tag.command.entity.OohTag;
import com.devoops.oopslog.tag.command.entity.OohTagPK;
import com.devoops.oopslog.tag.command.entity.OopsTag;
import com.devoops.oopslog.tag.command.entity.OopsTagPK;
import com.devoops.oopslog.tag.command.repository.OohTagRepository;
import com.devoops.oopslog.tag.command.repository.OopsTagRepository;
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
public class OopsCommandService {

    private final OopsCommandRepository oopsCommandRepository;
    private final MemberCommandRepository memberCommandRepository;
    private final OohTagRepository oohTagRepository;
    private final OopsTagRepository oopsTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public OopsCommandCreateDTO insertOops(OopsCommandCreateDTO oopsCommandCreateDTO) {

        // 최소 검증
        if (oopsCommandCreateDTO.getOopsUserId() == null)
            throw new IllegalArgumentException("작성자 정보가 없습니다(oopsUserId).");
        if (oopsCommandCreateDTO.getOopsTitle() == null || oopsCommandCreateDTO.getOopsTitle().isBlank())
            throw new IllegalArgumentException("제목은 필수입니다.");
        if (oopsCommandCreateDTO.getOopsContent() == null || oopsCommandCreateDTO.getOopsContent().isBlank())
            throw new IllegalArgumentException("내용은 필수입니다.");
        if (oopsCommandCreateDTO.getTagIds() != null && oopsCommandCreateDTO.getTagIds().size() > 3) {
            throw new IllegalArgumentException("태그는 최대 3개까지만 선택할 수 있습니다.");
        }

        // 회원 정보 없으면 저장 못하게함
        boolean exists = memberCommandRepository.existsById(oopsCommandCreateDTO.getOopsUserId());
        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다: " + oopsCommandCreateDTO.getOopsUserId());
        }

        // 저장할 정보 및 body에 담을 것
        OopsCommandEntity entity = new OopsCommandEntity();
        entity.setOopsUserId(oopsCommandCreateDTO.getOopsUserId());
        entity.setOopsTitle(oopsCommandCreateDTO.getOopsTitle());
        entity.setOopsContent(oopsCommandCreateDTO.getOopsContent());

        entity.setOopsIsPrivate(oopsCommandCreateDTO.getOopsIsPrivate()
                != null ? oopsCommandCreateDTO.getOopsIsPrivate() : "N");
        entity.setOopsCreateDate(oopsCommandCreateDTO.getOopsCreateDate());
        entity.setOopsModifyDate(oopsCommandCreateDTO.getOopsModifyDate());

        OopsCommandEntity saved = oopsCommandRepository.save(entity);


        if (oopsCommandCreateDTO.getTagIds() != null && !oopsCommandCreateDTO.getTagIds().isEmpty()) {
            log.info("tagIds: {}", oopsCommandCreateDTO.getTagIds());

            for (Long tagId : oopsCommandCreateDTO.getTagIds()) {


                // 복합키 생성
                log.info("생성된 oopsID: {}", saved.getOopsId());
                log.info("태그: {}", tagId);
                OopsTagPK oopsTagPK = new OopsTagPK(tagId, saved.getOopsId());

                // 관계 엔티티 생성 및 저장
                OopsTag oopsTag = new OopsTag();
                oopsTag.setOopsTagPK(oopsTagPK);

                oopsTagRepository.save(oopsTag);
            }
        }

        String name = memberCommandRepository.findById(saved.getOopsUserId())
                .map(m -> m.getName())
                .orElse(null);

        // 응답
        OopsCommandCreateDTO reqSaved = new OopsCommandCreateDTO();

        reqSaved.setOopsId(saved.getOopsId());
        reqSaved.setOopsUserId(saved.getOopsUserId());
        reqSaved.setOopsTitle(saved.getOopsTitle());
        reqSaved.setOopsContent(saved.getOopsContent());
        reqSaved.setOopsIsPrivate(saved.getOopsIsPrivate());
        reqSaved.setOopsAIAnswer(saved.getOopsAIAnswer());
        reqSaved.setOopsCreateDate(saved.getOopsCreateDate());
        reqSaved.setOopsModifyDate(saved.getOopsModifyDate());
        reqSaved.setOopsIsDeleted(saved.getOopsIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    @Transactional
    public OopsCommandResponseDTO updateOops(Long id, OopsCommandResponseDTO oopsCommandResponseDTO) {
        OopsCommandEntity oops = oopsCommandRepository.findById(id.longValue())
                .orElseThrow(() ->
                        new EntityNotFoundException("수정할 기록을 찾을 수 없습니다. id=" + id));

        // 부분 갱신(보낸 값만 적용 가능)
        if (oopsCommandResponseDTO.getOopsTitle()
                != null && !oopsCommandResponseDTO.getOopsTitle().isBlank()) {
            oops.setOopsTitle(oopsCommandResponseDTO.getOopsTitle().trim());
        }
        if (oopsCommandResponseDTO.getOopsContent()
                != null && !oopsCommandResponseDTO.getOopsContent().isBlank()) {
            oops.setOopsContent(oopsCommandResponseDTO.getOopsContent().trim());
        }
        if (oopsCommandResponseDTO.getOopsIsPrivate()
                != null) {
            oops.setOopsIsPrivate(oopsCommandResponseDTO.getOopsIsPrivate());
            // "Y"/"N"만 오게 검증하려면 별도 Validator 사용해서 코드 수정하기
        }
        if (oopsCommandResponseDTO.getTagIds() != null && oopsCommandResponseDTO.getTagIds().size() > 3) {
            throw new IllegalArgumentException("태그는 최대 3개까지만 선택할 수 있습니다.");
        }

        // 수정할 정보
        OopsCommandEntity saved = oopsCommandRepository.save(oops);


        // 게시글에 달린 기존 태그들
        List<Long> originalTagIds = oopsTagRepository.findTagIdsByOopsId(saved.getOopsId());

        // 변경될 태그 목록
        List<Long> newTagIds = oopsCommandResponseDTO.getTagIds();

        // 추가 및 삭제를 위해 태그 집합 비교
        Set<Long> originalSet = new HashSet<>(originalTagIds);
        Set<Long> newSet = new HashSet<>(newTagIds);

        Set<Long> toAdd = new HashSet<>(newSet);
        toAdd.removeAll(originalSet);

        Set<Long> toDelete = new HashSet<>(originalSet);
        toDelete.removeAll(newSet);

        // 삭제
        for(Long tagId : toDelete){
            OopsTagPK oopsTagPK = new OopsTagPK(tagId, saved.getOopsId());
            oopsTagRepository.deleteById(oopsTagPK);
        }

        // 추가
        for(Long tagId : toAdd){
            OopsTagPK pk = new OopsTagPK(tagId, saved.getOopsId());
            OopsTag oopsTag = new OopsTag();
            oopsTag.setOopsTagPK(pk);
            oopsTagRepository.save(oopsTag);
        }


        String name = memberCommandRepository.findById(saved.getOopsUserId())
                .map(m -> m.getName())
                .orElse(null);

        // 응답 부분
        OopsCommandResponseDTO reqSaved = new OopsCommandResponseDTO();
        reqSaved.setOopsId(saved.getOopsId());
        reqSaved.setOopsUserId(saved.getOopsUserId());
        reqSaved.setOopsTitle(saved.getOopsTitle());
        reqSaved.setOopsContent(saved.getOopsContent());
        reqSaved.setOopsIsPrivate(saved.getOopsIsPrivate());
        reqSaved.setOopsAIAnswer(saved.getOopsAIAnswer());
        reqSaved.setOopsCreateDate(saved.getOopsCreateDate());
        reqSaved.setOopsModifyDate(saved.getOopsModifyDate());  // 수정일 이거 업데이트됨
        reqSaved.setOopsIsDeleted(saved.getOopsIsDeleted());
        reqSaved.setName(name);
        return reqSaved;
    }

    // 소프트 delete (is_deleted 에서 Y로 적용)
    @Transactional
    public void deleteOops(Long id) {
        OopsCommandEntity oops = oopsCommandRepository.findById(id.longValue())
                    .orElseThrow(() -> new EntityNotFoundException("삭제할 기록을 찾을 수 없습니다. id=" + id));

            // 소프트 삭제
            if (!"Y".equalsIgnoreCase(oops.getOopsIsDeleted())) {
                oops.setOopsIsDeleted("Y");
                oopsCommandRepository.save(oops); // @PreUpdate로 modify_date 갱신
            }
        }
    // 하드 delete (DB에서 지워버림 관리자 페이지에서 사용할 듯)
    @Transactional
    public void hardDeleteOops(Long id) {
        if (!oopsCommandRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 기록이 없습니다. id=" + id);
        }
        oopsCommandRepository.deleteById(id);
    }
}

