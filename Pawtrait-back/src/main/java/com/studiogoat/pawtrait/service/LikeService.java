package com.studiogoat.pawtrait.service;

import com.studiogoat.pawtrait.domain.Like;
import com.studiogoat.pawtrait.repository.LikeRepository;
import com.studiogoat.pawtrait.service.dto.LikeDTO;
import com.studiogoat.pawtrait.service.mapper.LikeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Like}.
 */
@Service
public class LikeService {

    private final Logger log = LoggerFactory.getLogger(LikeService.class);

    private final LikeRepository likeRepository;

    private final LikeMapper likeMapper;

    public LikeService(LikeRepository likeRepository, LikeMapper likeMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
    }

    /**
     * Save a like.
     *
     * @param likeDTO the entity to save.
     * @return the persisted entity.
     */
    public LikeDTO save(LikeDTO likeDTO) {
        log.debug("Request to save Like : {}", likeDTO);
        Like like = likeMapper.toEntity(likeDTO);
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    /**
     * Update a like.
     *
     * @param likeDTO the entity to save.
     * @return the persisted entity.
     */
    public LikeDTO update(LikeDTO likeDTO) {
        log.debug("Request to update Like : {}", likeDTO);
        Like like = likeMapper.toEntity(likeDTO);
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    /**
     * Partially update a like.
     *
     * @param likeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LikeDTO> partialUpdate(LikeDTO likeDTO) {
        log.debug("Request to partially update Like : {}", likeDTO);

        return likeRepository
            .findById(likeDTO.getId())
            .map(existingLike -> {
                likeMapper.partialUpdate(existingLike, likeDTO);

                return existingLike;
            })
            .map(likeRepository::save)
            .map(likeMapper::toDto);
    }

    /**
     * Get all the likes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LikeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Likes");
        return likeRepository.findAll(pageable).map(likeMapper::toDto);
    }

    /**
     * Get one like by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<LikeDTO> findOne(String id) {
        log.debug("Request to get Like : {}", id);
        return likeRepository.findById(id).map(likeMapper::toDto);
    }

    /**
     * Delete the like by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Like : {}", id);
        likeRepository.deleteById(id);
    }
}
