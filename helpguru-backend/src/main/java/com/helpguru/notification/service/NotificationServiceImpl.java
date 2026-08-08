package com.helpguru.notification.service;

import com.helpguru.notification.domain.NotificationEntity;
import com.helpguru.notification.domain.NotificationStatusEnum;
import com.helpguru.notification.dto.NotificationDto;
import com.helpguru.notification.dto.SendNotificationRequest;
import com.helpguru.notification.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    @Transactional
    public NotificationDto sendNotification(SendNotificationRequest request) {
        NotificationEntity notification = NotificationEntity.builder()
                .recipientUserId(request.getRecipientUserId())
                .title(request.getTitle())
                .message(request.getMessage())
                .channel(request.getChannel())
                .status(NotificationStatusEnum.SENT)
                .referenceType(request.getReferenceType())
                .referenceId(request.getReferenceId())
                .isRead(false)
                .isDeleted(false)
                .build();

        NotificationEntity saved = notificationRepository.save(notification);
        NotificationDto dto = mapToDto(saved);

        // Real-time WebSocket push to recipient's private topic channel
        try {
            messagingTemplate.convertAndSend("/topic/notifications/" + request.getRecipientUserId(), dto);
        } catch (Exception ignored) {
            // Silently fall back if no active websocket subscriber
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByRecipient(Long recipientUserId) {
        return notificationRepository.findByRecipientUserIdAndIsDeletedFalseOrderByCreatedAtDesc(recipientUserId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotificationsByRecipient(Long recipientUserId) {
        return notificationRepository.findByRecipientUserIdAndIsReadFalseAndIsDeletedFalseOrderByCreatedAtDesc(recipientUserId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDto markAsRead(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .filter(n -> !n.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        notification.setIsRead(true);
        NotificationEntity updated = notificationRepository.save(notification);
        return mapToDto(updated);
    }

    @Override
    public void broadcastEvent(String topic, Object eventPayload) {
        try {
            messagingTemplate.convertAndSend(topic, eventPayload);
        } catch (Exception ignored) {
            // Fallback for broadcast events
        }
    }

    private NotificationDto mapToDto(NotificationEntity n) {
        return NotificationDto.builder()
                .id(n.getId())
                .recipientUserId(n.getRecipientUserId())
                .title(n.getTitle())
                .message(n.getMessage())
                .channel(n.getChannel())
                .status(n.getStatus())
                .referenceType(n.getReferenceType())
                .referenceId(n.getReferenceId())
                .isRead(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
