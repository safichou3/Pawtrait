package com.studiogoat.pawtrait.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.studiogoat.pawtrait.domain.PhotoTag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoTagDTO implements Serializable {

    private String id;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private TagDTO user;

    private PhotoDTO photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public TagDTO getUser() {
        return user;
    }

    public void setUser(TagDTO user) {
        this.user = user;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoTagDTO)) {
            return false;
        }

        PhotoTagDTO photoTagDTO = (PhotoTagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, photoTagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoTagDTO{" +
            "id='" + getId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", user=" + getUser() +
            ", photo=" + getPhoto() +
            "}";
    }
}
