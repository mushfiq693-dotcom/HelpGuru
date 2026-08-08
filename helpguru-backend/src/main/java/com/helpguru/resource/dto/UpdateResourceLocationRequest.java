package com.helpguru.resource.dto;

import com.helpguru.resource.domain.ResourceStatusEnum;
import jakarta.validation.constraints.NotNull;

public class UpdateResourceLocationRequest {

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private Double speedKmh;
    private Double headingDegrees;
    private ResourceStatusEnum status;

    public UpdateResourceLocationRequest() {}

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Double speedKmh) { this.speedKmh = speedKmh; }

    public Double getHeadingDegrees() { return headingDegrees; }
    public void setHeadingDegrees(Double headingDegrees) { this.headingDegrees = headingDegrees; }

    public ResourceStatusEnum getStatus() { return status; }
    public void setStatus(ResourceStatusEnum status) { this.status = status; }
}
