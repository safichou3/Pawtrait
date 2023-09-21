package com.studiogoat.pawtrait.web.rest;

import com.studiogoat.pawtrait.repository.PhotoTagRepository;
import com.studiogoat.pawtrait.service.PhotoTagService;
import com.studiogoat.pawtrait.service.dto.PhotoTagDTO;
import com.studiogoat.pawtrait.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.studiogoat.pawtrait.domain.PhotoTag}.
 */
@RestController
@RequestMapping("/api")
public class PhotoTagResource {

    private final Logger log = LoggerFactory.getLogger(PhotoTagResource.class);

    private static final String ENTITY_NAME = "photoTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotoTagService photoTagService;

    private final PhotoTagRepository photoTagRepository;

    public PhotoTagResource(PhotoTagService photoTagService, PhotoTagRepository photoTagRepository) {
        this.photoTagService = photoTagService;
        this.photoTagRepository = photoTagRepository;
    }

    /**
     * {@code POST  /photo-tags} : Create a new photoTag.
     *
     * @param photoTagDTO the photoTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photoTagDTO, or with status {@code 400 (Bad Request)} if the photoTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/photo-tags")
    public ResponseEntity<PhotoTagDTO> createPhotoTag(@RequestBody PhotoTagDTO photoTagDTO) throws URISyntaxException {
        log.debug("REST request to save PhotoTag : {}", photoTagDTO);
        if (photoTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new photoTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotoTagDTO result = photoTagService.save(photoTagDTO);
        return ResponseEntity
            .created(new URI("/api/photo-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /photo-tags/:id} : Updates an existing photoTag.
     *
     * @param id the id of the photoTagDTO to save.
     * @param photoTagDTO the photoTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoTagDTO,
     * or with status {@code 400 (Bad Request)} if the photoTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photoTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/photo-tags/{id}")
    public ResponseEntity<PhotoTagDTO> updatePhotoTag(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PhotoTagDTO photoTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhotoTag : {}, {}", id, photoTagDTO);
        if (photoTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photoTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhotoTagDTO result = photoTagService.update(photoTagDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, photoTagDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /photo-tags/:id} : Partial updates given fields of an existing photoTag, field will ignore if it is null
     *
     * @param id the id of the photoTagDTO to save.
     * @param photoTagDTO the photoTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoTagDTO,
     * or with status {@code 400 (Bad Request)} if the photoTagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the photoTagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the photoTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/photo-tags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhotoTagDTO> partialUpdatePhotoTag(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PhotoTagDTO photoTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhotoTag partially : {}, {}", id, photoTagDTO);
        if (photoTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photoTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhotoTagDTO> result = photoTagService.partialUpdate(photoTagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, photoTagDTO.getId())
        );
    }

    /**
     * {@code GET  /photo-tags} : get all the photoTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photoTags in body.
     */
    @GetMapping("/photo-tags")
    public ResponseEntity<List<PhotoTagDTO>> getAllPhotoTags(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PhotoTags");
        Page<PhotoTagDTO> page = photoTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /photo-tags/:id} : get the "id" photoTag.
     *
     * @param id the id of the photoTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photoTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/photo-tags/{id}")
    public ResponseEntity<PhotoTagDTO> getPhotoTag(@PathVariable String id) {
        log.debug("REST request to get PhotoTag : {}", id);
        Optional<PhotoTagDTO> photoTagDTO = photoTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photoTagDTO);
    }

    /**
     * {@code DELETE  /photo-tags/:id} : delete the "id" photoTag.
     *
     * @param id the id of the photoTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/photo-tags/{id}")
    public ResponseEntity<Void> deletePhotoTag(@PathVariable String id) {
        log.debug("REST request to delete PhotoTag : {}", id);
        photoTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
