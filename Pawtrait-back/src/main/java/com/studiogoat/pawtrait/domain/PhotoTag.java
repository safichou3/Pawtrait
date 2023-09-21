package com.studiogoat.pawtrait.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PhotoTag.
 */
@Document(collection = "photo_tag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @Field("deleted_at")
    private Instant deletedAt;

    @DBRef
    @Field("user")
    private Tag user;

    @DBRef
    @Field("photo")
    @JsonIgnoreProperties(value = { "user", "category" }, allowSetters = true)
    private Photo photo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PhotoTag id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PhotoTag createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PhotoTag updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PhotoTag deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Tag getUser() {
        return this.user;
    }

    public void setUser(Tag tag) {
        this.user = tag;
    }

    public PhotoTag user(Tag tag) {
        this.setUser(tag);
        return this;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public PhotoTag photo(Photo photo) {
        this.setPhoto(photo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoTag)) {
            return false;
        }
        return id != null && id.equals(((PhotoTag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoTag{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
