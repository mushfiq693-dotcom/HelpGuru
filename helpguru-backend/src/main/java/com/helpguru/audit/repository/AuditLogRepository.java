package com.helpguru.audit.repository;

import com.helpguru.audit.domain.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
    List<AuditLogEntity> findTop50ByOrderByCreatedAtDesc();
    List<AuditLogEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AuditLogEntity> findByEntityNameAndEntityIdOrderByCreatedAtDesc(String entityName, Long entityId);
}
