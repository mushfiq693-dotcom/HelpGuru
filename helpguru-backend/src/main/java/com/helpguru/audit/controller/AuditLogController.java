package com.helpguru.audit.controller;

import com.helpguru.audit.dto.AuditLogDto;
import com.helpguru.audit.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
@Tag(name = "Audit Log System", description = "Endpoints for national security audit trails, user actions, and system activity logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get recent system audit logs (Admin only)")
    public ResponseEntity<List<AuditLogDto>> getRecentAuditLogs() {
        return ResponseEntity.ok(auditLogService.getRecentAuditLogs());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get audit logs for a specific user ID (Admin only)")
    public ResponseEntity<List<AuditLogDto>> getAuditLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(userId));
    }
}
