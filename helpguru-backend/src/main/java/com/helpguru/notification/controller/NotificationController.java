package com.helpguru.notification.controller;

import com.helpguru.notification.dto.NotificationDto;
import com.helpguru.notification.dto.SendNotificationRequest;
import com.helpguru.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification & Event Broadcasting", description = "Endpoints for emergency alerts, push notifications, and user inbox management")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Send emergency notification alert to user (WebSocket, Push, SMS)")
    public ResponseEntity<NotificationDto> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        NotificationDto notification = notificationService.sendNotification(request);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notification inbox for user")
    public ResponseEntity<List<NotificationDto>> getNotificationsByRecipient(@PathVariable Long userId, @RequestParam(defaultValue = "false") boolean unreadOnly) {
        if (unreadOnly) {
            return ResponseEntity.ok(notificationService.getUnreadNotificationsByRecipient(userId));
        }
        return ResponseEntity.ok(notificationService.getNotificationsByRecipient(userId));
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long id) {
        NotificationDto updated = notificationService.markAsRead(id);
        return ResponseEntity.ok(updated);
    }
}
