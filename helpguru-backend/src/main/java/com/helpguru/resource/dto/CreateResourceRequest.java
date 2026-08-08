package com.helpguru.resource.dto;

import com.helpguru.resource.domain.ResourceTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateResourceRequest {

    @NotBlank(message = "Resource code is required")
    private String resourceCode;

    @NotBlank(message = "Resource name is required")
    private String name;

    @NotNull(message = "Resource type is required")
    private ResourceTypeEnum resourceType;

    @NotNull(message = "Region ID is required")
    private Long regionId;

    private Long hospitalId;

    @NotNull(message = "Latitude is required")
    private Double currentLatitude;

    @NotNull(message = "Longitude is required")
    private Double currentLongitude;

    private Integer capacity = 1;
    private Double speedKmh = 60.0;

    public CreateResourceRequest() {}

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

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Double speedKmh) { this.speedKmh = speedKmh; }
}
