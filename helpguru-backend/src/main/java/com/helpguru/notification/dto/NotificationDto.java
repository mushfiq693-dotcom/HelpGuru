package com.helpguru.notification.dto;

import com.helpguru.notification.domain.NotificationChannelEnum;
import com.helpguru.notification.domain.NotificationStatusEnum;

import java.time.Instant;

public class NotificationDto {
    private Long id;
    private Long recipientUserId;
    private String title;
    private String message;
    private NotificationChannelEnum channel;
    private NotificationStatusEnum status;
    private String referenceType;
    private Long referenceId;
    private Boolean isRead;
    private Instant createdAt;

    public NotificationDto() {}

    public NotificationDto(Long id, Long recipientUserId, String title, String message, NotificationChannelEnum channel, NotificationStatusEnum status, String referenceType, Long referenceId, Boolean isRead, Instant createdAt) {
        this.id = id;
        this.recipientUserId = recipientUserId;
        this.title = title;
        this.message = message;
        this.channel = channel;
        this.status = status;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecipientUserId() { return recipientUserId; }
    public void setRecipientUserId(Long recipientUserId) { this.recipientUserId = recipientUserId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationChannelEnum getChannel() { return channel; }
    public void setChannel(NotificationChannelEnum channel) { this.channel = channel; }

    public NotificationStatusEnum getStatus() { return status; }
    public void setStatus(NotificationStatusEnum status) { this.status = status; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long recipientUserId;
        private String title;
        private String message;
        private NotificationChannelEnum channel;
        private NotificationStatusEnum status;
        private String referenceType;
        private Long referenceId;
        private Boolean isRead;
        private Instant createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder recipientUserId(Long recipientUserId) { this.recipientUserId = recipientUserId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder channel(NotificationChannelEnum channel) { this.channel = channel; return this; }
        public Builder status(NotificationStatusEnum status) { this.status = status; return this; }
        public Builder referenceType(String referenceType) { this.referenceType = referenceType; return this; }
        public Builder referenceId(Long referenceId) { this.referenceId = referenceId; return this; }
        public Builder isRead(Boolean isRead) { this.isRead = isRead; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public NotificationDto build() {
            return new NotificationDto(id, recipientUserId, title, message, channel, status, referenceType, referenceId, isRead, createdAt);
        }
    }
}
