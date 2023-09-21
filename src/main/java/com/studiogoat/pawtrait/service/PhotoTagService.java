package com.studiogoat.pawtrait.service;

import com.studiogoat.pawtrait.domain.PhotoTag;
import com.studiogoat.pawtrait.repository.PhotoTagRepository;
import com.studiogoat.pawtrait.service.dto.PhotoTagDTO;
import com.studiogoat.pawtrait.service.mapper.PhotoTagMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PhotoTag}.
 */
@Service
public class PhotoTagService {

    private final Logger log = LoggerFactory.getLogger(PhotoTagService.class);

    private final PhotoTagRepository photoTagRepository;

    private final PhotoTagMapper photoTagMapper;

    public PhotoTagService(PhotoTagRepository photoTagRepository, PhotoTagMapper photoTagMapper) {
        this.photoTagRepository = photoTagRepository;
        this.photoTagMapper = photoTagMapper;
    }

    /**
     * Save a photoTag.
     *
     * @param photoTagDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoTagDTO save(PhotoTagDTO photoTagDTO) {
        log.debug("Request to save PhotoTag : {}", photoTagDTO);
        PhotoTag photoTag = photoTagMapper.toEntity(photoTagDTO);
        photoTag = photoTagRepository.save(photoTag);
        return photoTagMapper.toDto(photoTag);
    }

    /**
     * Update a photoTag.
     *
     * @param photoTagDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoTagDTO update(PhotoTagDTO photoTagDTO) {
        log.debug("Request to update PhotoTag : {}", photoTagDTO);
        PhotoTag photoTag = photoTagMapper.toEntity(photoTagDTO);
        photoTag = photoTagRepository.save(photoTag);
        return photoTagMapper.toDto(photoTag);
    }

    /**
     * Partially update a photoTag.
     *
     * @param photoTagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhotoTagDTO> partialUpdate(PhotoTagDTO photoTagDTO) {
        log.debug("Request to partially update PhotoTag : {}", photoTagDTO);

        return photoTagRepository
            .findById(photoTagDTO.getId())
            .map(existingPhotoTag -> {
                photoTagMapper.partialUpdate(existingPhotoTag, photoTagDTO);

                return existingPhotoTag;
            })
            .map(photoTagRepository::save)
            .map(photoTagMapper::toDto);
    }

    /**
     * Get all the photoTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PhotoTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhotoTags");
        return photoTagRepository.findAll(pageable).map(photoTagMapper::toDto);
    }

    /**
     * Get one photoTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhotoTagDTO> findOne(String id) {
        log.debug("Request to get PhotoTag : {}", id);
        return photoTagRepository.findById(id).map(photoTagMapper::toDto);
    }

    /**
     * Delete the photoTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PhotoTag : {}", id);
        photoTagRepository.deleteById(id);
    }
}
