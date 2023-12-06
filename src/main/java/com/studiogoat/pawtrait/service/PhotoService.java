package com.studiogoat.pawtrait.service;

import com.studiogoat.pawtrait.domain.Category;
import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.domain.User;
import com.studiogoat.pawtrait.repository.PhotoRepository;
import com.studiogoat.pawtrait.service.dto.CategoryDTO;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.dto.UserDTO;
import com.studiogoat.pawtrait.service.mapper.PhotoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    private PhotoDTO toDto(Photo photo) {
        PhotoDTO dto = new PhotoDTO();

        dto.setId(photo.getId());
        dto.setPhotoUrl(photo.getPhotoUrl());
        dto.setDescription(photo.getDescription());
        dto.setCuteness(photo.getCuteness());
        dto.setCreatedAt(photo.getCreatedAt());
        dto.setUpdatedAt(photo.getUpdatedAt());
        dto.setDeletedAt(photo.getDeletedAt());

        if (photo.getCategory() != null) {
            CategoryDTO categoryDTO = toCategoryDto(photo.getCategory());
            dto.setCategory(categoryDTO);
        }

        if (photo.getUser() != null) {
            UserDTO userDTO = toUserDto(photo.getUser());
            dto.setUser(userDTO);
        }

        return dto;
    }

    private CategoryDTO toCategoryDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setCreatedAt(category.getCreatedAt());
        categoryDTO.setUpdatedAt(category.getUpdatedAt());
        categoryDTO.setDeletedAt(category.getDeletedAt());
        return categoryDTO;
    }

    private UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Update a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO update(PhotoDTO photoDTO) {
        log.debug("Request to update Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Partially update a photo.
     *
     * @param photoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhotoDTO> partialUpdate(PhotoDTO photoDTO) {
        log.debug("Request to partially update Photo : {}", photoDTO);

        return photoRepository
            .findById(photoDTO.getId())
            .map(existingPhoto -> {
                photoMapper.partialUpdate(existingPhoto, photoDTO);

                return existingPhoto;
            })
            .map(photoRepository::save)
            .map(photoMapper::toDto);
    }

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PhotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photoRepository.findAll(pageable).map(photoMapper::toDto);
    }

    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhotoDTO> findOne(String id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findById(id).map(photoMapper::toDto);
    }

    /**
     * Get one photo by id with category.
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhotoDTO> findOneWithCategory(String id) {
        return photoRepository.findById(id).map(this::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
    }
}
