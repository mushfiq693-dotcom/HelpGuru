package com.helpguru.resource.repository;

import com.helpguru.resource.domain.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByResourceId(Long resourceId);
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
