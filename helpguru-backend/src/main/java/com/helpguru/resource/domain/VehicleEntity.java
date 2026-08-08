package com.helpguru.resource.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false, unique = true)
    private Long resourceId;

    @Column(name = "license_plate", nullable = false, unique = true, length = 50)
    private String licensePlate;

    @Column(length = 100)
    private String model;

    @Column(name = "fuel_level_percent")
    private Double fuelLevelPercent = 100.0;

    @Column(name = "equipment_level", length = 50)
    private String equipmentLevel = "ADVANCED_LIFE_SUPPORT";

    @Column(name = "last_serviced_at")
    private Instant lastServicedAt;

    public VehicleEntity() {}

    public VehicleEntity(Long id, Long resourceId, String licensePlate, String model, Double fuelLevelPercent, String equipmentLevel, Instant lastServicedAt) {
        this.id = id;
        this.resourceId = resourceId;
        this.licensePlate = licensePlate;
        this.model = model;
        this.fuelLevelPercent = fuelLevelPercent != null ? fuelLevelPercent : 100.0;
        this.equipmentLevel = equipmentLevel != null ? equipmentLevel : "ADVANCED_LIFE_SUPPORT";
        this.lastServicedAt = lastServicedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Double getFuelLevelPercent() { return fuelLevelPercent; }
    public void setFuelLevelPercent(Double fuelLevelPercent) { this.fuelLevelPercent = fuelLevelPercent; }

    public String getEquipmentLevel() { return equipmentLevel; }
    public void setEquipmentLevel(String equipmentLevel) { this.equipmentLevel = equipmentLevel; }

    public Instant getLastServicedAt() { return lastServicedAt; }
    public void setLastServicedAt(Instant lastServicedAt) { this.lastServicedAt = lastServicedAt; }
}
