package com.helpguru.incident.repository;

import com.helpguru.incident.domain.IncidentEntity;
import com.helpguru.incident.domain.IncidentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    Optional<IncidentEntity> findByIncidentCodeAndIsDeletedFalse(String incidentCode);
    List<IncidentEntity> findByRegionIdAndIsDeletedFalse(Long regionId);
    List<IncidentEntity> findByStatusAndIsDeletedFalse(IncidentStatusEnum status);
    List<IncidentEntity> findByRegionIdAndStatusAndIsDeletedFalse(Long regionId, IncidentStatusEnum status);
}
