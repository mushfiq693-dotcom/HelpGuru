package com.helpguru.audit.service;

import com.helpguru.audit.domain.AuditLogEntity;
import com.helpguru.audit.dto.AuditLogDto;
import com.helpguru.audit.repository.AuditLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Async
    @Transactional
    public void logAction(Long userId, String username, String action, String entityName, Long entityId, String ipAddress, String details) {
        AuditLogEntity log = AuditLogEntity.builder()
                .userId(userId)
                .username(username)
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .ipAddress(ipAddress)
                .details(details)
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogDto> getRecentAuditLogs() {
        return auditLogRepository.findTop50ByOrderByCreatedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogDto> getAuditLogsByUser(Long userId) {
        return auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AuditLogDto mapToDto(AuditLogEntity a) {
        return AuditLogDto.builder()
                .id(a.getId())
                .userId(a.getUserId())
                .username(a.getUsername())
                .action(a.getAction())
                .entityName(a.getEntityName())
                .entityId(a.getEntityId())
                .ipAddress(a.getIpAddress())
                .details(a.getDetails())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
