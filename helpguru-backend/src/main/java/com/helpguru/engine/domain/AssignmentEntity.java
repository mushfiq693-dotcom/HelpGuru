package com.helpguru.engine.domain;

import com.helpguru.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class AssignmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_code", nullable = false, unique = true, length = 50)
    private String assignmentCode;

    @Column(name = "incident_id", nullable = false)
    private Long incidentId;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "priority_score", nullable = false)
    private Double calculatedScore = 0.0;

    @Column(name = "estimated_distance_km", nullable = false)
    private Double estimatedDistanceKm = 0.0;

    @Column(name = "estimated_travel_time_minutes", nullable = false)
    private Double estimatedEtaMinutes = 0.0;

    @Column(name = "assigned_by_user_id")
    private Long assignedByUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssignmentStatusEnum status = AssignmentStatusEnum.PROPOSED;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public AssignmentEntity() {}

    public AssignmentEntity(Long id, String assignmentCode, Long incidentId, Long resourceId, Long hospitalId, Double calculatedScore, Double estimatedDistanceKm, Double estimatedEtaMinutes, Long assignedByUserId, AssignmentStatusEnum status, Boolean isDeleted) {
        this.id = id;
        this.assignmentCode = assignmentCode;
        this.incidentId = incidentId;
        this.resourceId = resourceId;
        this.hospitalId = hospitalId;
        this.calculatedScore = calculatedScore != null ? calculatedScore : 0.0;
        this.estimatedDistanceKm = estimatedDistanceKm != null ? estimatedDistanceKm : 0.0;
        this.estimatedEtaMinutes = estimatedEtaMinutes != null ? estimatedEtaMinutes : 0.0;
        this.assignedByUserId = assignedByUserId;
        this.status = status != null ? status : AssignmentStatusEnum.PROPOSED;
        this.isDeleted = isDeleted != null ? isDeleted : false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssignmentCode() { return assignmentCode; }
    public void setAssignmentCode(String assignmentCode) { this.assignmentCode = assignmentCode; }

    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }

    public Long getHospitalId() { return hospitalId; }
    public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }

    public Double getCalculatedScore() { return calculatedScore; }
    public void setCalculatedScore(Double calculatedScore) { this.calculatedScore = calculatedScore; }

    public Double getEstimatedDistanceKm() { return estimatedDistanceKm; }
    public void setEstimatedDistanceKm(Double estimatedDistanceKm) { this.estimatedDistanceKm = estimatedDistanceKm; }

    public Double getEstimatedEtaMinutes() { return estimatedEtaMinutes; }
    public void setEstimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; }

    public Long getAssignedByUserId() { return assignedByUserId; }
    public void setAssignedByUserId(Long assignedByUserId) { this.assignedByUserId = assignedByUserId; }

    public AssignmentStatusEnum getStatus() { return status; }
    public void setStatus(AssignmentStatusEnum status) { this.status = status; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String assignmentCode;
        private Long incidentId;
        private Long resourceId;
        private Long hospitalId;
        private Double calculatedScore = 0.0;
        private Double estimatedDistanceKm = 0.0;
        private Double estimatedEtaMinutes = 0.0;
        private Long assignedByUserId;
        private AssignmentStatusEnum status = AssignmentStatusEnum.PROPOSED;
        private Boolean isDeleted = false;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder assignmentCode(String assignmentCode) { this.assignmentCode = assignmentCode; return this; }
        public Builder incidentId(Long incidentId) { this.incidentId = incidentId; return this; }
        public Builder resourceId(Long resourceId) { this.resourceId = resourceId; return this; }
        public Builder hospitalId(Long hospitalId) { this.hospitalId = hospitalId; return this; }
        public Builder calculatedScore(Double calculatedScore) { this.calculatedScore = calculatedScore; return this; }
        public Builder estimatedDistanceKm(Double estimatedDistanceKm) { this.estimatedDistanceKm = estimatedDistanceKm; return this; }
        public Builder estimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; return this; }
        public Builder assignedByUserId(Long assignedByUserId) { this.assignedByUserId = assignedByUserId; return this; }
        public Builder status(AssignmentStatusEnum status) { this.status = status; return this; }
        public Builder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public AssignmentEntity build() {
            return new AssignmentEntity(id, assignmentCode, incidentId, resourceId, hospitalId, calculatedScore, estimatedDistanceKm, estimatedEtaMinutes, assignedByUserId, status, isDeleted);
        }
    }
}
