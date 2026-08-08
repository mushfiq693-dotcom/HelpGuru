package com.helpguru.resource.dto;

import com.helpguru.resource.domain.ResourceStatusEnum;
import com.helpguru.resource.domain.ResourceTypeEnum;

import java.time.Instant;

public class ResourceDto {
    private Long id;
    private String resourceCode;
    private String name;
    private ResourceTypeEnum resourceType;
    private Long regionId;
    private Long hospitalId;
    private Double currentLatitude;
    private Double currentLongitude;
    private ResourceStatusEnum status;
    private Integer capacity;
    private Double speedKmh;
    private Instant createdAt;
    private Instant updatedAt;

    public ResourceDto() {}

    public ResourceDto(Long id, String resourceCode, String name, ResourceTypeEnum resourceType, Long regionId, Long hospitalId, Double currentLatitude, Double currentLongitude, ResourceStatusEnum status, Integer capacity, Double speedKmh, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.resourceCode = resourceCode;
        this.name = name;
        this.resourceType = resourceType;
        this.regionId = regionId;
        this.hospitalId = hospitalId;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.status = status;
        this.capacity = capacity;
        this.speedKmh = speedKmh;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getResourceCode() { return resourceCode; }
    public void setResourceCode(String resourceCode) { this.resourceCode = resourceCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ResourceTypeEnum getResourceType() { return resourceType; }
    public void setResourceType(ResourceTypeEnum resourceType) { this.resourceType = resourceType; }

    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }

    public Long getHospitalId() { return hospitalId; }
    public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }

    public Double getCurrentLatitude() { return currentLatitude; }
    public void setCurrentLatitude(Double currentLatitude) { this.currentLatitude = currentLatitude; }

    public Double getCurrentLongitude() { return currentLongitude; }
    public void setCurrentLongitude(Double currentLongitude) { this.currentLongitude = currentLongitude; }

    public ResourceStatusEnum getStatus() { return status; }
    public void setStatus(ResourceStatusEnum status) { this.status = status; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Double speedKmh) { this.speedKmh = speedKmh; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String resourceCode;
        private String name;
        private ResourceTypeEnum resourceType;
        private Long regionId;
        private Long hospitalId;
        private Double currentLatitude;
        private Double currentLongitude;
        private ResourceStatusEnum status;
        private Integer capacity;
        private Double speedKmh;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder resourceCode(String resourceCode) { this.resourceCode = resourceCode; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder resourceType(ResourceTypeEnum resourceType) { this.resourceType = resourceType; return this; }
        public Builder regionId(Long regionId) { this.regionId = regionId; return this; }
        public Builder hospitalId(Long hospitalId) { this.hospitalId = hospitalId; return this; }
        public Builder currentLatitude(Double currentLatitude) { this.currentLatitude = currentLatitude; return this; }
        public Builder currentLongitude(Double currentLongitude) { this.currentLongitude = currentLongitude; return this; }
        public Builder status(ResourceStatusEnum status) { this.status = status; return this; }
        public Builder capacity(Integer capacity) { this.capacity = capacity; return this; }
        public Builder speedKmh(Double speedKmh) { this.speedKmh = speedKmh; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public ResourceDto build() {
            return new ResourceDto(id, resourceCode, name, resourceType, regionId, hospitalId, currentLatitude, currentLongitude, status, capacity, speedKmh, createdAt, updatedAt);
        }
    }
}
