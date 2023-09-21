package com.studiogoat.pawtrait.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.studiogoat.pawtrait.IntegrationTest;
import com.studiogoat.pawtrait.domain.Like;
import com.studiogoat.pawtrait.repository.LikeRepository;
import com.studiogoat.pawtrait.service.dto.LikeDTO;
import com.studiogoat.pawtrait.service.mapper.LikeMapper;
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
 * Integration tests for the {@link LikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LikeResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private MockMvc restLikeMockMvc;

    private Like like;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createEntity() {
        Like like = new Like().createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT).deletedAt(DEFAULT_DELETED_AT);
        return like;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createUpdatedEntity() {
        Like like = new Like().createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);
        return like;
    }

    @BeforeEach
    public void initTest() {
        likeRepository.deleteAll();
        like = createEntity();
    }

    @Test
    void createLike() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();
        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);
        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate + 1);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLike.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLike.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    void createLikeWithExistingId() throws Exception {
        // Create the Like with an existing ID
        like.setId("existing_id");
        LikeDTO likeDTO = likeMapper.toDto(like);

        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        // Get all the likeList
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    void getLike() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        // Get the like
        restLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLike() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like
        Like updatedLike = likeRepository.findById(like.getId()).get();
        updatedLike.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);
        LikeDTO likeDTO = likeMapper.toDto(updatedLike);

        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLike.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLike.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void putNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike.createdAt(UPDATED_CREATED_AT);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLike.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLike.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    void fullUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLike.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLike.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    void patchNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, likeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(UUID.randomUUID().toString());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLike() throws Exception {
        // Initialize the database
        likeRepository.save(like);

        int databaseSizeBeforeDelete = likeRepository.findAll().size();

        // Delete the like
        restLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, like.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
