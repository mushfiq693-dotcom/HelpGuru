package com.helpguru.incident.dto;

import com.helpguru.resource.domain.ResourceTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateIncidentRequest {

    @NotBlank(message = "Incident title is required")
    private String title;

    private String description;

    @NotNull(message = "Severity level is required (1 to 10)")
    @Min(value = 1, message = "Severity level minimum is 1")
    @Max(value = 10, message = "Severity level maximum is 10")
    private Integer severityLevel;

    private Integer affectedPeopleCount = 1;
    private String timeSensitivityLevel = "HIGH";

    @NotNull(message = "Required resource type is required")
    private ResourceTypeEnum requiredResourceType;

    @NotNull(message = "Region ID is required")
    private Long regionId;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String addressText;

    public CreateIncidentRequest() {}

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
}
