package com.helpguru.incident.dto;

import com.helpguru.incident.domain.IncidentStatusEnum;
import com.helpguru.resource.domain.ResourceTypeEnum;

import java.time.Instant;

public class IncidentDto {
    private Long id;
    private String incidentCode;
    private String title;
    private String description;
    private Integer severityLevel;
    private Integer affectedPeopleCount;
    private String timeSensitivityLevel;
    private ResourceTypeEnum requiredResourceType;
    private Long regionId;
    private Double latitude;
    private Double longitude;
    private String addressText;
    private IncidentStatusEnum status;
    private Long reportedByUserId;
    private Instant createdAt;
    private Instant updatedAt;

    public IncidentDto() {}

    public IncidentDto(Long id, String incidentCode, String title, String description, Integer severityLevel, Integer affectedPeopleCount, String timeSensitivityLevel, ResourceTypeEnum requiredResourceType, Long regionId, Double latitude, Double longitude, String addressText, IncidentStatusEnum status, Long reportedByUserId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.incidentCode = incidentCode;
        this.title = title;
        this.description = description;
        this.severityLevel = severityLevel;
        this.affectedPeopleCount = affectedPeopleCount;
        this.timeSensitivityLevel = timeSensitivityLevel;
        this.requiredResourceType = requiredResourceType;
        this.regionId = regionId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressText = addressText;
        this.status = status;
        this.reportedByUserId = reportedByUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIncidentCode() { return incidentCode; }
    public void setIncidentCode(String incidentCode) { this.incidentCode = incidentCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(Integer severityLevel) { this.severityLevel = severityLevel; }

    public Integer getAffectedPeopleCount() { return affectedPeopleCount; }
    public void setAffectedPeopleCount(Integer affectedPeopleCount) { this.affectedPeopleCount = affectedPeopleCount; }

    public String getTimeSensitivityLevel() { return timeSensitivityLevel; }
    public void setTimeSensitivityLevel(String timeSensitivityLevel) { this.timeSensitivityLevel = timeSensitivityLevel; }

    public ResourceTypeEnum getRequiredResourceType() { return requiredResourceType; }
    public void setRequiredResourceType(ResourceTypeEnum requiredResourceType) { this.requiredResourceType = requiredResourceType; }

    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getAddressText() { return addressText; }
    public void setAddressText(String addressText) { this.addressText = addressText; }

    public IncidentStatusEnum getStatus() { return status; }
    public void setStatus(IncidentStatusEnum status) { this.status = status; }

    public Long getReportedByUserId() { return reportedByUserId; }
    public void setReportedByUserId(Long reportedByUserId) { this.reportedByUserId = reportedByUserId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String incidentCode;
        private String title;
        private String description;
        private Integer severityLevel;
        private Integer affectedPeopleCount;
        private String timeSensitivityLevel;
        private ResourceTypeEnum requiredResourceType;
        private Long regionId;
        private Double latitude;
        private Double longitude;
        private String addressText;
        private IncidentStatusEnum status;
        private Long reportedByUserId;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder incidentCode(String incidentCode) { this.incidentCode = incidentCode; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder severityLevel(Integer severityLevel) { this.severityLevel = severityLevel; return this; }
        public Builder affectedPeopleCount(Integer affectedPeopleCount) { this.affectedPeopleCount = affectedPeopleCount; return this; }
        public Builder timeSensitivityLevel(String timeSensitivityLevel) { this.timeSensitivityLevel = timeSensitivityLevel; return this; }
        public Builder requiredResourceType(ResourceTypeEnum requiredResourceType) { this.requiredResourceType = requiredResourceType; return this; }
        public Builder regionId(Long regionId) { this.regionId = regionId; return this; }
        public Builder latitude(Double latitude) { this.latitude = latitude; return this; }
        public Builder longitude(Double longitude) { this.longitude = longitude; return this; }
        public Builder addressText(String addressText) { this.addressText = addressText; return this; }
        public Builder status(IncidentStatusEnum status) { this.status = status; return this; }
        public Builder reportedByUserId(Long reportedByUserId) { this.reportedByUserId = reportedByUserId; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public IncidentDto build() {
            return new IncidentDto(id, incidentCode, title, description, severityLevel, affectedPeopleCount, timeSensitivityLevel, requiredResourceType, regionId, latitude, longitude, addressText, status, reportedByUserId, createdAt, updatedAt);
        }
    }
}
