package com.studiogoat.pawtrait.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Report.
 */
@Document(collection = "report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("description")
    private String description;

    @Field("resolution_date")
    private Instant resolutionDate;

    @Field("status")
    private String status;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @Field("deleted_at")
    private Instant deletedAt;

    @DBRef
    @Field("reportedBy")
    private User reportedBy;

    @DBRef
    @Field("resolvedBy")
    private User resolvedBy;

    @DBRef
    @Field("photo")
    @JsonIgnoreProperties(value = { "user", "category" }, allowSetters = true)
    private Photo photo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Report id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Report type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public Report description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getResolutionDate() {
        return this.resolutionDate;
    }

    public Report resolutionDate(Instant resolutionDate) {
        this.setResolutionDate(resolutionDate);
        return this;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getStatus() {
        return this.status;
    }

    public Report status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Report createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Report updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Report deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getReportedBy() {
        return this.reportedBy;
    }

    public void setReportedBy(User user) {
        this.reportedBy = user;
    }

    public Report reportedBy(User user) {
        this.setReportedBy(user);
        return this;
    }

    public User getResolvedBy() {
        return this.resolvedBy;
    }

    public void setResolvedBy(User user) {
        this.resolvedBy = user;
    }

    public Report resolvedBy(User user) {
        this.setResolvedBy(user);
        return this;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Report photo(Photo photo) {
        this.setPhoto(photo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", resolutionDate='" + getResolutionDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
