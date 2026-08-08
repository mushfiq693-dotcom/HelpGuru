package com.helpguru.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateHospitalRequest {

    @NotBlank(message = "Hospital name is required")
    private String name;

    @NotBlank(message = "Hospital code is required")
    private String code;

    @NotNull(message = "Region ID is required")
    private Long regionId;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private Integer totalBeds = 50;
    private Integer availableBeds = 50;
    private Integer icuTotal = 10;
    private Integer icuAvailable = 10;
    private String emergencyContact;

    public CreateHospitalRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getTotalBeds() { return totalBeds; }
    public void setTotalBeds(Integer totalBeds) { this.totalBeds = totalBeds; }

    public Integer getAvailableBeds() { return availableBeds; }
    public void setAvailableBeds(Integer availableBeds) { this.availableBeds = availableBeds; }

    public Integer getIcuTotal() { return icuTotal; }
    public void setIcuTotal(Integer icuTotal) { this.icuTotal = icuTotal; }

    public Integer getIcuAvailable() { return icuAvailable; }
    public void setIcuAvailable(Integer icuAvailable) { this.icuAvailable = icuAvailable; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
}
