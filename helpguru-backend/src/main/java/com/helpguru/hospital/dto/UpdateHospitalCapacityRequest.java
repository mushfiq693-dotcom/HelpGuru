package com.helpguru.hospital.dto;

import com.helpguru.hospital.domain.HospitalStatusEnum;
import jakarta.validation.constraints.NotNull;

public class UpdateHospitalCapacityRequest {

    @NotNull(message = "Available beds count is required")
    private Integer availableBeds;

    @NotNull(message = "Available ICU count is required")
    private Integer icuAvailable;

    private HospitalStatusEnum status;

    public UpdateHospitalCapacityRequest() {}

    public Integer getAvailableBeds() { return availableBeds; }
    public void setAvailableBeds(Integer availableBeds) { this.availableBeds = availableBeds; }

    public Integer getIcuAvailable() { return icuAvailable; }
    public void setIcuAvailable(Integer icuAvailable) { this.icuAvailable = icuAvailable; }

    public HospitalStatusEnum getStatus() { return status; }
    public void setStatus(HospitalStatusEnum status) { this.status = status; }
}
