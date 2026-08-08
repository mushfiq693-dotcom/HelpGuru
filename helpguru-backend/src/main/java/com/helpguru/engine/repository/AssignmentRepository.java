package com.helpguru.engine.repository;

import com.helpguru.engine.domain.AssignmentEntity;
import com.helpguru.engine.domain.AssignmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
    List<AssignmentEntity> findByIncidentIdAndIsDeletedFalse(Long incidentId);
    List<AssignmentEntity> findByResourceIdAndIsDeletedFalse(Long resourceId);
    List<AssignmentEntity> findByStatusAndIsDeletedFalse(AssignmentStatusEnum status);
    Optional<AssignmentEntity> findByIncidentIdAndResourceIdAndStatusInAndIsDeletedFalse(Long incidentId, Long resourceId, List<AssignmentStatusEnum> statuses);
}
