package com.studiogoat.pawtrait.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.studiogoat.pawtrait.domain.Report} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportDTO implements Serializable {

    private String id;

    private String type;

    private String description;

    private Instant resolutionDate;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private UserDTO reportedBy;

    private UserDTO resolvedBy;

    private PhotoDTO photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public UserDTO getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(UserDTO reportedBy) {
        this.reportedBy = reportedBy;
    }

    public UserDTO getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(UserDTO resolvedBy) {
        this.resolvedBy = resolvedBy;
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
        if (!(o instanceof ReportDTO)) {
            return false;
        }

        ReportDTO reportDTO = (ReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", resolutionDate='" + getResolutionDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", reportedBy=" + getReportedBy() +
            ", resolvedBy=" + getResolvedBy() +
            ", photo=" + getPhoto() +
            "}";
    }
}
