package com.helpguru.resource.domain;

import com.helpguru.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class ResourceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_code", nullable = false, unique = true, length = 50)
    private String resourceCode;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false, length = 50)
    private ResourceTypeEnum resourceType;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "current_latitude", nullable = false)
    private Double currentLatitude;

    @Column(name = "current_longitude", nullable = false)
    private Double currentLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ResourceStatusEnum status = ResourceStatusEnum.AVAILABLE;

    @Column(nullable = false)
    private Integer capacity = 1;

    @Column(name = "speed_kmh", nullable = false)
    private Double speedKmh = 60.0;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public ResourceEntity() {}

    public ResourceEntity(Long id, String resourceCode, String name, ResourceTypeEnum resourceType, Long regionId, Long hospitalId, Double currentLatitude, Double currentLongitude, ResourceStatusEnum status, Integer capacity, Double speedKmh, Boolean isDeleted) {
        this.id = id;
        this.resourceCode = resourceCode;
        this.name = name;
        this.resourceType = resourceType;
        this.regionId = regionId;
        this.hospitalId = hospitalId;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.status = status != null ? status : ResourceStatusEnum.AVAILABLE;
        this.capacity = capacity != null ? capacity : 1;
        this.speedKmh = speedKmh != null ? speedKmh : 60.0;
        this.isDeleted = isDeleted != null ? isDeleted : false;
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

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

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
        private ResourceStatusEnum status = ResourceStatusEnum.AVAILABLE;
        private Integer capacity = 1;
        private Double speedKmh = 60.0;
        private Boolean isDeleted = false;

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
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public ResourceEntity build() {
            return new ResourceEntity(id, resourceCode, name, resourceType, regionId, hospitalId, currentLatitude, currentLongitude, status, capacity, speedKmh, isDeleted);
        }
    }
}
