package com.helpguru.resource.telemetry;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "gps_tracking_logs")
public class GpsTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "speed_kmh")
    private Double speedKmh = 0.0;

    @Column(name = "heading_degrees")
    private Double headingDegrees = 0.0;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt = Instant.now();

    public GpsTrackingEntity() {}

    public GpsTrackingEntity(Long id, Long resourceId, Double latitude, Double longitude, Double speedKmh, Double headingDegrees, Instant recordedAt) {
        this.id = id;
        this.resourceId = resourceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speedKmh = speedKmh != null ? speedKmh : 0.0;
        this.headingDegrees = headingDegrees != null ? headingDegrees : 0.0;
        this.recordedAt = recordedAt != null ? recordedAt : Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Double speedKmh) { this.speedKmh = speedKmh; }

    public Double getHeadingDegrees() { return headingDegrees; }
    public void setHeadingDegrees(Double headingDegrees) { this.headingDegrees = headingDegrees; }

    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant recordedAt) { this.recordedAt = recordedAt; }
}
