package com.helpguru.notification.dto;

import com.helpguru.notification.domain.NotificationChannelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendNotificationRequest {

    @NotNull(message = "Recipient user ID is required")
    private Long recipientUserId;

    @NotBlank(message = "Notification title is required")
    private String title;

    @NotBlank(message = "Notification message is required")
    private String message;

    private NotificationChannelEnum channel = NotificationChannelEnum.WEBSOCKET;
    private String referenceType;
    private Long referenceId;

    public SendNotificationRequest() {}

    public Long getRecipientUserId() { return recipientUserId; }
    public void setRecipientUserId(Long recipientUserId) { this.recipientUserId = recipientUserId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationChannelEnum getChannel() { return channel; }
    public void setChannel(NotificationChannelEnum channel) { this.channel = channel; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }
}
