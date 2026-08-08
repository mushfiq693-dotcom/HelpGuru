package com.helpguru.engine.dto;

import com.helpguru.engine.domain.AssignmentStatusEnum;
import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.incident.dto.IncidentDto;
import com.helpguru.resource.dto.ResourceDto;

import java.time.Instant;

public class AssignmentDto {
    private Long id;
    private String assignmentCode;
    private IncidentDto incident;
    private ResourceDto resource;
    private HospitalDto hospital;
    private Double calculatedScore;
    private Double estimatedEtaMinutes;
    private Long assignedByUserId;
    private AssignmentStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    public AssignmentDto() {}

    public AssignmentDto(Long id, String assignmentCode, IncidentDto incident, ResourceDto resource, HospitalDto hospital, Double calculatedScore, Double estimatedEtaMinutes, Long assignedByUserId, AssignmentStatusEnum status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.assignmentCode = assignmentCode;
        this.incident = incident;
        this.resource = resource;
        this.hospital = hospital;
        this.calculatedScore = calculatedScore;
        this.estimatedEtaMinutes = estimatedEtaMinutes;
        this.assignedByUserId = assignedByUserId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssignmentCode() { return assignmentCode; }
    public void setAssignmentCode(String assignmentCode) { this.assignmentCode = assignmentCode; }

    public IncidentDto getIncident() { return incident; }
    public void setIncident(IncidentDto incident) { this.incident = incident; }

    public ResourceDto getResource() { return resource; }
    public void setResource(ResourceDto resource) { this.resource = resource; }

    public HospitalDto getHospital() { return hospital; }
    public void setHospital(HospitalDto hospital) { this.hospital = hospital; }

    public Double getCalculatedScore() { return calculatedScore; }
    public void setCalculatedScore(Double calculatedScore) { this.calculatedScore = calculatedScore; }

    public Double getEstimatedEtaMinutes() { return estimatedEtaMinutes; }
    public void setEstimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; }

    public Long getAssignedByUserId() { return assignedByUserId; }
    public void setAssignedByUserId(Long assignedByUserId) { this.assignedByUserId = assignedByUserId; }

    public AssignmentStatusEnum getStatus() { return status; }
    public void setStatus(AssignmentStatusEnum status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String assignmentCode;
        private IncidentDto incident;
        private ResourceDto resource;
        private HospitalDto hospital;
        private Double calculatedScore;
        private Double estimatedEtaMinutes;
        private Long assignedByUserId;
        private AssignmentStatusEnum status;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder assignmentCode(String assignmentCode) { this.assignmentCode = assignmentCode; return this; }
        public Builder incident(IncidentDto incident) { this.incident = incident; return this; }
        public Builder resource(ResourceDto resource) { this.resource = resource; return this; }
        public Builder hospital(HospitalDto hospital) { this.hospital = hospital; return this; }
        public Builder calculatedScore(Double calculatedScore) { this.calculatedScore = calculatedScore; return this; }
        public Builder estimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; return this; }
        public Builder assignedByUserId(Long assignedByUserId) { this.assignedByUserId = assignedByUserId; return this; }
        public Builder status(AssignmentStatusEnum status) { this.status = status; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public AssignmentDto build() {
            return new AssignmentDto(id, assignmentCode, incident, resource, hospital, calculatedScore, estimatedEtaMinutes, assignedByUserId, status, createdAt, updatedAt);
        }
    }
}
