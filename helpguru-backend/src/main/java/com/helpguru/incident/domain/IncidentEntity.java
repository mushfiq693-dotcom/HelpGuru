package com.helpguru.incident.domain;

import com.helpguru.common.domain.BaseEntity;
import com.helpguru.resource.domain.ResourceTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "incidents")
public class IncidentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "incident_code", nullable = false, unique = true, length = 50)
    private String incidentCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "severity_level", nullable = false)
    private Integer severityLevel = 5;

    @Column(name = "affected_people_count", nullable = false)
    private Integer affectedPeopleCount = 1;

    @Column(name = "time_sensitivity_level", nullable = false, length = 30)
    private String timeSensitivityLevel = "HIGH";

    @Enumerated(EnumType.STRING)
    @Column(name = "required_resource_type", nullable = false, length = 50)
    private ResourceTypeEnum requiredResourceType;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "address_text", columnDefinition = "TEXT")
    private String addressText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IncidentStatusEnum status = IncidentStatusEnum.REPORTED;

    @Column(name = "reported_by_user_id")
    private Long reportedByUserId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public IncidentEntity() {}

    public IncidentEntity(Long id, String incidentCode, String title, String description, Integer severityLevel, Integer affectedPeopleCount, String timeSensitivityLevel, ResourceTypeEnum requiredResourceType, Long regionId, Double latitude, Double longitude, String addressText, IncidentStatusEnum status, Long reportedByUserId, Boolean isDeleted) {
        this.id = id;
        this.incidentCode = incidentCode;
        this.title = title;
        this.description = description;
        this.severityLevel = severityLevel != null ? severityLevel : 5;
        this.affectedPeopleCount = affectedPeopleCount != null ? affectedPeopleCount : 1;
        this.timeSensitivityLevel = timeSensitivityLevel != null ? timeSensitivityLevel : "HIGH";
        this.requiredResourceType = requiredResourceType;
        this.regionId = regionId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressText = addressText;
        this.status = status != null ? status : IncidentStatusEnum.REPORTED;
        this.reportedByUserId = reportedByUserId;
        this.isDeleted = isDeleted != null ? isDeleted : false;
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

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String incidentCode;
        private String title;
        private String description;
        private Integer severityLevel = 5;
        private Integer affectedPeopleCount = 1;
        private String timeSensitivityLevel = "HIGH";
        private ResourceTypeEnum requiredResourceType;
        private Long regionId;
        private Double latitude;
        private Double longitude;
        private String addressText;
        private IncidentStatusEnum status = IncidentStatusEnum.REPORTED;
        private Long reportedByUserId;
        private Boolean isDeleted = false;

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
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public IncidentEntity build() {
            return new IncidentEntity(id, incidentCode, title, description, severityLevel, affectedPeopleCount, timeSensitivityLevel, requiredResourceType, regionId, latitude, longitude, addressText, status, reportedByUserId, isDeleted);
        }
    }
}
