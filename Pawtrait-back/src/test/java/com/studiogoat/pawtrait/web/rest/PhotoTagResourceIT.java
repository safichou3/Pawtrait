package com.studiogoat.pawtrait.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.studiogoat.pawtrait.IntegrationTest;
import com.studiogoat.pawtrait.domain.PhotoTag;
import com.studiogoat.pawtrait.repository.PhotoTagRepository;
import com.studiogoat.pawtrait.service.dto.PhotoTagDTO;
import com.studiogoat.pawtrait.service.mapper.PhotoTagMapper;
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
 * Integration tests for the {@link PhotoTagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhotoTagResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/photo-tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PhotoTagRepository photoTagRepository;

    @Autowired
    private PhotoTagMapper photoTagMapper;

    @Autowired
    private MockMvc restPhotoTagMockMvc;

    private PhotoTag photoTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoTag createEntity() {
        PhotoTag photoTag = new PhotoTag().createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT).deletedAt(DEFAULT_DELETED_AT);
        return photoTag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhotoTag createUpdatedEntity() {
        PhotoTag photoTag = new PhotoTag().createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);
        return photoTag;
    }

    @BeforeEach
    public void initTest() {
        photoTagRepository.deleteAll();
        photoTag = createEntity();
    }

    @Test
    void createPhotoTag() throws Exception {
        int databaseSizeBeforeCreate = photoTagRepository.findAll().size();
        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);
        restPhotoTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoTagDTO)))
            .andExpect(status().isCreated());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeCreate + 1);
        PhotoTag testPhotoTag = photoTagList.get(photoTagList.size() - 1);
        assertThat(testPhotoTag.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPhotoTag.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPhotoTag.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    void createPhotoTagWithExistingId() throws Exception {
        // Create the PhotoTag with an existing ID
        photoTag.setId("existing_id");
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        int databaseSizeBeforeCreate = photoTagRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoTagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPhotoTags() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        // Get all the photoTagList
        restPhotoTagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photoTag.getId())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    void getPhotoTag() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        // Get the photoTag
        restPhotoTagMockMvc
            .perform(get(ENTITY_API_URL_ID, photoTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photoTag.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    void getNonExistingPhotoTag() throws Exception {
        // Get the photoTag
        restPhotoTagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPhotoTag() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();

        // Update the photoTag
        PhotoTag updatedPhotoTag = photoTagRepository.findById(photoTag.getId()).get();
        updatedPhotoTag.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(updatedPhotoTag);

        restPhotoTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
        PhotoTag testPhotoTag = photoTagList.get(photoTagList.size() - 1);
        assertThat(testPhotoTag.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPhotoTag.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPhotoTag.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void putNonExistingPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photoTagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photoTagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePhotoTagWithPatch() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();

        // Update the photoTag using partial update
        PhotoTag partialUpdatedPhotoTag = new PhotoTag();
        partialUpdatedPhotoTag.setId(photoTag.getId());

        partialUpdatedPhotoTag.updatedAt(UPDATED_UPDATED_AT);

        restPhotoTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotoTag))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
        PhotoTag testPhotoTag = photoTagList.get(photoTagList.size() - 1);
        assertThat(testPhotoTag.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPhotoTag.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPhotoTag.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    void fullUpdatePhotoTagWithPatch() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();

        // Update the photoTag using partial update
        PhotoTag partialUpdatedPhotoTag = new PhotoTag();
        partialUpdatedPhotoTag.setId(photoTag.getId());

        partialUpdatedPhotoTag.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);

        restPhotoTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotoTag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotoTag))
            )
            .andExpect(status().isOk());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
        PhotoTag testPhotoTag = photoTagList.get(photoTagList.size() - 1);
        assertThat(testPhotoTag.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPhotoTag.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPhotoTag.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void patchNonExistingPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, photoTagDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPhotoTag() throws Exception {
        int databaseSizeBeforeUpdate = photoTagRepository.findAll().size();
        photoTag.setId(UUID.randomUUID().toString());

        // Create the PhotoTag
        PhotoTagDTO photoTagDTO = photoTagMapper.toDto(photoTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotoTagMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(photoTagDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhotoTag in the database
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePhotoTag() throws Exception {
        // Initialize the database
        photoTagRepository.save(photoTag);

        int databaseSizeBeforeDelete = photoTagRepository.findAll().size();

        // Delete the photoTag
        restPhotoTagMockMvc
            .perform(delete(ENTITY_API_URL_ID, photoTag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhotoTag> photoTagList = photoTagRepository.findAll();
        assertThat(photoTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
