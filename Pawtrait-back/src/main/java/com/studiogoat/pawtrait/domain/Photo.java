package com.studiogoat.pawtrait.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Photo.
 */
@Document(collection = "photo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("photo_url")
    private String photoUrl;

    @Field("description")
    private String description;

    @Field("cuteness")
    private Long cuteness;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @Field("deleted_at")
    private Instant deletedAt;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("category")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Photo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public Photo photoUrl(String photoUrl) {
        this.setPhotoUrl(photoUrl);
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public Photo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCuteness() {
        return this.cuteness;
    }

    public Photo cuteness(Long cuteness) {
        this.setCuteness(cuteness);
        return this;
    }

    public void setCuteness(Long cuteness) {
        this.cuteness = cuteness;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Photo createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Photo updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Photo deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Photo user(User user) {
        this.setUser(user);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Photo category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", cuteness=" + getCuteness() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
