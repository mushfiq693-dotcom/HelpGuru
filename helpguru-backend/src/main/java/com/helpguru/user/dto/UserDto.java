package com.helpguru.user.dto;

import java.time.Instant;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Long regionId;
    private Boolean isActive;
    private Set<String> roles;
    private Instant createdAt;
    private Instant updatedAt;

    public UserDto() {}

    public UserDto(Long id, String username, String email, String fullName, String phoneNumber, Long regionId, Boolean isActive, Set<String> roles, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.regionId = regionId;
        this.isActive = isActive;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String phoneNumber;
        private Long regionId;
        private Boolean isActive;
        private Set<String> roles;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder regionId(Long regionId) { this.regionId = regionId; return this; }
        public Builder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public Builder roles(Set<String> roles) { this.roles = roles; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public UserDto build() {
            return new UserDto(id, username, email, fullName, phoneNumber, regionId, isActive, roles, createdAt, updatedAt);
        }
    }
}
