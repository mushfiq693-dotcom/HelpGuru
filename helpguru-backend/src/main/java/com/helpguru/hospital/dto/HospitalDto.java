package com.helpguru.hospital.dto;

import com.helpguru.hospital.domain.HospitalStatusEnum;

import java.time.Instant;

public class HospitalDto {
    private Long id;
    private String name;
    private String code;
    private Long regionId;
    private Double latitude;
    private Double longitude;
    private Integer totalBeds;
    private Integer availableBeds;
    private Integer icuTotal;
    private Integer icuAvailable;
    private String emergencyContact;
    private HospitalStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    public HospitalDto() {}

    public HospitalDto(Long id, String name, String code, Long regionId, Double latitude, Double longitude, Integer totalBeds, Integer availableBeds, Integer icuTotal, Integer icuAvailable, String emergencyContact, HospitalStatusEnum status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.regionId = regionId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalBeds = totalBeds;
        this.availableBeds = availableBeds;
        this.icuTotal = icuTotal;
        this.icuAvailable = icuAvailable;
        this.emergencyContact = emergencyContact;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public HospitalStatusEnum getStatus() { return status; }
    public void setStatus(HospitalStatusEnum status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String code;
        private Long regionId;
        private Double latitude;
        private Double longitude;
        private Integer totalBeds;
        private Integer availableBeds;
        private Integer icuTotal;
        private Integer icuAvailable;
        private String emergencyContact;
        private HospitalStatusEnum status;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder code(String code) { this.code = code; return this; }
        public Builder regionId(Long regionId) { this.regionId = regionId; return this; }
        public Builder latitude(Double latitude) { this.latitude = latitude; return this; }
        public Builder longitude(Double longitude) { this.longitude = longitude; return this; }
        public Builder totalBeds(Integer totalBeds) { this.totalBeds = totalBeds; return this; }
        public Builder availableBeds(Integer availableBeds) { this.availableBeds = availableBeds; return this; }
        public Builder icuTotal(Integer icuTotal) { this.icuTotal = icuTotal; return this; }
        public Builder icuAvailable(Integer icuAvailable) { this.icuAvailable = icuAvailable; return this; }
        public Builder emergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; return this; }
        public Builder status(HospitalStatusEnum status) { this.status = status; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public HospitalDto build() {
            return new HospitalDto(id, name, code, regionId, latitude, longitude, totalBeds, availableBeds, icuTotal, icuAvailable, emergencyContact, status, createdAt, updatedAt);
        }
    }
}
