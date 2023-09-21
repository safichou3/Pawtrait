package com.studiogoat.pawtrait.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.studiogoat.pawtrait.IntegrationTest;
import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.repository.PhotoRepository;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.mapper.PhotoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PhotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhotoResourceIT {

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CUTENESS = 1L;
    private static final Long UPDATED_CUTENESS = 2L;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/photos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private MockMvc restPhotoMockMvc;

    private Photo photo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photo createEntity() {
        Photo photo = new Photo()
            .photoUrl(DEFAULT_PHOTO_URL)
            .description(DEFAULT_DESCRIPTION)
            .cuteness(DEFAULT_CUTENESS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return photo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photo createUpdatedEntity() {
        Photo photo = new Photo()
            .photoUrl(UPDATED_PHOTO_URL)
            .description(UPDATED_DESCRIPTION)
            .cuteness(UPDATED_CUTENESS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return photo;
    }

    @BeforeEach
    public void initTest() {
        photoRepository.deleteAll();
        photo = createEntity();
    }

    @Test
    void createPhoto() throws Exception {
        int databaseSizeBeforeCreate = photoRepository.findAll().size();
        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);
        restPhotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isCreated());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeCreate + 1);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testPhoto.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhoto.getCuteness()).isEqualTo(DEFAULT_CUTENESS);
        assertThat(testPhoto.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPhoto.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPhoto.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    void createPhotoWithExistingId() throws Exception {
        // Create the Photo with an existing ID
        photo.setId("existing_id");
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        int databaseSizeBeforeCreate = photoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPhotos() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        // Get all the photoList
        restPhotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photo.getId())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cuteness").value(hasItem(DEFAULT_CUTENESS.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    void getPhoto() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        // Get the photo
        restPhotoMockMvc
            .perform(get(ENTITY_API_URL_ID, photo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photo.getId()))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cuteness").value(DEFAULT_CUTENESS.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    void getNonExistingPhoto() throws Exception {
        // Get the photo
        restPhotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPhoto() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Update the photo
        Photo updatedPhoto = photoRepository.findById(photo.getId()).get();
        updatedPhoto
            .photoUrl(UPDATED_PHOTO_URL)
            .description(UPDATED_DESCRIPTION)
            .cuteness(UPDATED_CUTENESS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        PhotoDTO photoDTO = photoMapper.toDto(updatedPhoto);

        restPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPhoto.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhoto.getCuteness()).isEqualTo(UPDATED_CUTENESS);
        assertThat(testPhoto.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPhoto.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPhoto.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void putNonExistingPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePhotoWithPatch() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Update the photo using partial update
        Photo partialUpdatedPhoto = new Photo();
        partialUpdatedPhoto.setId(photo.getId());

        partialUpdatedPhoto
            .photoUrl(UPDATED_PHOTO_URL)
            .cuteness(UPDATED_CUTENESS)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoto))
            )
            .andExpect(status().isOk());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPhoto.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhoto.getCuteness()).isEqualTo(UPDATED_CUTENESS);
        assertThat(testPhoto.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPhoto.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPhoto.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void fullUpdatePhotoWithPatch() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Update the photo using partial update
        Photo partialUpdatedPhoto = new Photo();
        partialUpdatedPhoto.setId(photo.getId());

        partialUpdatedPhoto
            .photoUrl(UPDATED_PHOTO_URL)
            .description(UPDATED_DESCRIPTION)
            .cuteness(UPDATED_CUTENESS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoto))
            )
            .andExpect(status().isOk());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPhoto.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhoto.getCuteness()).isEqualTo(UPDATED_CUTENESS);
        assertThat(testPhoto.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPhoto.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPhoto.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void patchNonExistingPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, photoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();
        photo.setId(UUID.randomUUID().toString());

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePhoto() throws Exception {
        // Initialize the database
        photoRepository.save(photo);

        int databaseSizeBeforeDelete = photoRepository.findAll().size();

        // Delete the photo
        restPhotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, photo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
