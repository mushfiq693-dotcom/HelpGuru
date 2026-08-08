package com.helpguru.user.domain;

import com.helpguru.common.domain.BaseEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity() {}

    public UserEntity(Long id, String username, String email, String passwordHash, String fullName, String phoneNumber, Long regionId, Boolean isActive, Boolean isDeleted, Instant deletedAt, Set<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.regionId = regionId;
        this.isActive = isActive != null ? isActive : true;
        this.isDeleted = isDeleted != null ? isDeleted : false;
        this.deletedAt = deletedAt;
        this.roles = roles != null ? roles : new HashSet<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Instant getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Instant deletedAt) { this.deletedAt = deletedAt; }

    public Set<RoleEntity> getRoles() { return roles; }
    public void setRoles(Set<RoleEntity> roles) { this.roles = roles; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String passwordHash;
        private String fullName;
        private String phoneNumber;
        private Long regionId;
        private Boolean isActive = true;
        private Boolean isDeleted = false;
        private Instant deletedAt;
        private Set<RoleEntity> roles = new HashSet<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder regionId(Long regionId) { this.regionId = regionId; return this; }
        public Builder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }
        public Builder deletedAt(Instant deletedAt) { this.deletedAt = deletedAt; return this; }
        public Builder roles(Set<RoleEntity> roles) { this.roles = roles; return this; }

        public UserEntity build() {
            return new UserEntity(id, username, email, passwordHash, fullName, phoneNumber, regionId, isActive, isDeleted, deletedAt, roles);
        }
    }
}
