package com.helpguru.hospital.domain;

import com.helpguru.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "hospitals")
public class HospitalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "total_beds", nullable = false)
    private Integer totalBeds = 0;

    @Column(name = "available_beds", nullable = false)
    private Integer availableBeds = 0;

    @Column(name = "icu_total", nullable = false)
    private Integer icuTotal = 0;

    @Column(name = "icu_available", nullable = false)
    private Integer icuAvailable = 0;

    @Column(name = "emergency_contact", length = 50)
    private String emergencyContact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HospitalStatusEnum status = HospitalStatusEnum.OPERATIONAL;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public HospitalEntity() {}

    public HospitalEntity(Long id, String name, String code, Long regionId, Double latitude, Double longitude, Integer totalBeds, Integer availableBeds, Integer icuTotal, Integer icuAvailable, String emergencyContact, HospitalStatusEnum status, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.regionId = regionId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalBeds = totalBeds != null ? totalBeds : 0;
        this.availableBeds = availableBeds != null ? availableBeds : 0;
        this.icuTotal = icuTotal != null ? icuTotal : 0;
        this.icuAvailable = icuAvailable != null ? icuAvailable : 0;
        this.emergencyContact = emergencyContact;
        this.status = status != null ? status : HospitalStatusEnum.OPERATIONAL;
        this.isDeleted = isDeleted != null ? isDeleted : false;
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

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String code;
        private Long regionId;
        private Double latitude;
        private Double longitude;
        private Integer totalBeds = 0;
        private Integer availableBeds = 0;
        private Integer icuTotal = 0;
        private Integer icuAvailable = 0;
        private String emergencyContact;
        private HospitalStatusEnum status = HospitalStatusEnum.OPERATIONAL;
        private Boolean isDeleted = false;

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
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public HospitalEntity build() {
            return new HospitalEntity(id, name, code, regionId, latitude, longitude, totalBeds, availableBeds, icuTotal, icuAvailable, emergencyContact, status, isDeleted);
        }
    }
}
