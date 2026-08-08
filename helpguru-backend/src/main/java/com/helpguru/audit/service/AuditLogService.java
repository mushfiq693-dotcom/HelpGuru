package com.helpguru.audit.service;

import com.helpguru.audit.dto.AuditLogDto;

import java.util.List;

public interface AuditLogService {
    void logAction(Long userId, String username, String action, String entityName, Long entityId, String ipAddress, String details);
    List<AuditLogDto> getRecentAuditLogs();
    List<AuditLogDto> getAuditLogsByUser(Long userId);
}
