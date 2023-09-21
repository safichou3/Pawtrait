package com.studiogoat.pawtrait.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.studiogoat.pawtrait.domain.Photo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoDTO implements Serializable {

    private String id;

    private String photoUrl;

    private String description;

    private Long cuteness;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private UserDTO user;

    private CategoryDTO category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCuteness() {
        return cuteness;
    }

    public void setCuteness(Long cuteness) {
        this.cuteness = cuteness;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoDTO)) {
            return false;
        }

        PhotoDTO photoDTO = (PhotoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, photoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoDTO{" +
            "id='" + getId() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", cuteness=" + getCuteness() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", user=" + getUser() +
            ", category=" + getCategory() +
            "}";
    }
}
