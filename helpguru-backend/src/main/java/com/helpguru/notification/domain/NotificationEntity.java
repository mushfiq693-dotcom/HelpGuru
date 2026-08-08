package com.helpguru.notification.domain;

import com.helpguru.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_user_id", nullable = false)
    private Long recipientUserId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationChannelEnum channel = NotificationChannelEnum.WEBSOCKET;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationStatusEnum status = NotificationStatusEnum.SENT;

    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public NotificationEntity() {}

    public NotificationEntity(Long id, Long recipientUserId, String title, String message, NotificationChannelEnum channel, NotificationStatusEnum status, String referenceType, Long referenceId, Boolean isRead, Boolean isDeleted) {
        this.id = id;
        this.recipientUserId = recipientUserId;
        this.title = title;
        this.message = message;
        this.channel = channel != null ? channel : NotificationChannelEnum.WEBSOCKET;
        this.status = status != null ? status : NotificationStatusEnum.SENT;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.isRead = isRead != null ? isRead : false;
        this.isDeleted = isDeleted != null ? isDeleted : false;
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

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long recipientUserId;
        private String title;
        private String message;
        private NotificationChannelEnum channel = NotificationChannelEnum.WEBSOCKET;
        private NotificationStatusEnum status = NotificationStatusEnum.SENT;
        private String referenceType;
        private Long referenceId;
        private Boolean isRead = false;
        private Boolean isDeleted = false;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder recipientUserId(Long recipientUserId) { this.recipientUserId = recipientUserId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder channel(NotificationChannelEnum channel) { this.channel = channel; return this; }
        public Builder status(NotificationStatusEnum status) { this.status = status; return this; }
        public Builder referenceType(String referenceType) { this.referenceType = referenceType; return this; }
        public Builder referenceId(Long referenceId) { this.referenceId = referenceId; return this; }
        public Builder isRead(Boolean isRead) { this.isRead = isRead; return this; }
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public NotificationEntity build() {
            return new NotificationEntity(id, recipientUserId, title, message, channel, status, referenceType, referenceId, isRead, isDeleted);
        }
    }
}
