package com.helpguru.resource.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsTrackingRepository extends JpaRepository<GpsTrackingEntity, Long> {
    List<GpsTrackingEntity> findTop10ByResourceIdOrderByRecordedAtDesc(Long resourceId);
}
