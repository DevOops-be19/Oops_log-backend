package com.devoops.oopslog.likes.command.service;

import com.devoops.oopslog.likes.command.dto.LikesDTO;
import com.devoops.oopslog.likes.command.entity.Likes;
import com.devoops.oopslog.likes.command.repository.LikesRepository;
import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikesCommandServiceImpl implements LikesCommandService {
    private final LikesRepository likesRepository;
    private final OopsCommandRepository oopsCommandRepository;
    private final ModelMapper modelMapper;
    private final OohCommandRepository oohCommandRepository;

    @Autowired
    public LikesCommandServiceImpl(LikesRepository likesRepository,
                                   OopsCommandRepository oopsCommandRepository,
                                   ModelMapper modelMapper, OohCommandRepository oohCommandRepository) {
        this.likesRepository = likesRepository;
        this.oopsCommandRepository = oopsCommandRepository;
        this.modelMapper = modelMapper;
        this.oohCommandRepository = oohCommandRepository;
    }

    @Override
    @Transactional
    public String createOrDeleteLikesByOopsId(int oopsId, long userId) {

        log.info("좋아요 등록 및 삭제 시 기록 ID 확인: {}", oopsId);
        Likes likes = likesRepository.findByOopsId((long)oopsId);
        if (likes != null && likes.getUser_id() == userId) {
            likesRepository.delete(likes);
            return "likes deleted";
        } else {

            LikesDTO likesDTO = new LikesDTO();
            log.info("좋아요 등록 시 기록 ID 확인: {}", oopsId);
            likesDTO.setOopsId((long)oopsId);
            likesDTO.setUser_id(userId);

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            likes = modelMapper.map(likesDTO, Likes.class);

            likesRepository.save(likes);
            return "likes created";
        }
    }

    @Override
    @Transactional
    public String createOrDeleteLikesByOohId(int oohId, long userId) {

        Likes likes = likesRepository.findByOohId((long)oohId);
        if (likes != null && likes.getUser_id() == userId) {
            likesRepository.delete(likes);
            return "likes deleted";
        } else {

            LikesDTO likesDTO = new LikesDTO();
            likesDTO.setOohId((long)oohId);
            likesDTO.setUser_id(userId);

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            likes = modelMapper.map(likesDTO, Likes.class);

            likesRepository.save(likes);
            return "likes created";
        }
    }

    @Override
    public String isOopsLikeExist(int oopsId, long userId) {
        OopsCommandEntity oops = oopsCommandRepository.findById((long)oopsId).get();

        Likes likes = likesRepository.findByOopsId(oops.getOopsId());
        if (likes != null && likes.getUser_id() == userId) {
            return "exist";
        } else {
            return "does not exist";
        }
    }

    @Override
    public String isOohLikeExist(int oohId, long userId) {
        OohCommandEntity ooh = oohCommandRepository.findById((long)oohId).get();

        Likes likes = likesRepository.findByOohId(ooh.getOohId());
        if (likes != null && likes.getUser_id() == userId) {
            return "exist";
        } else {
            return "does not exist";
        }
    }
}
