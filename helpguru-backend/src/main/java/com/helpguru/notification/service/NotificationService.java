package com.helpguru.notification.service;

import com.helpguru.notification.dto.NotificationDto;
import com.helpguru.notification.dto.SendNotificationRequest;

import java.util.List;

public interface NotificationService {
    NotificationDto sendNotification(SendNotificationRequest request);
    List<NotificationDto> getNotificationsByRecipient(Long recipientUserId);
    List<NotificationDto> getUnreadNotificationsByRecipient(Long recipientUserId);
    NotificationDto markAsRead(Long notificationId);
    void broadcastEvent(String topic, Object eventPayload);
}
