package com.helpguru.engine.dto;

import jakarta.validation.constraints.NotNull;

public class CreateAssignmentRequest {

    @NotNull(message = "Incident ID is required")
    private Long incidentId;

    @NotNull(message = "Resource ID is required")
    private Long resourceId;

    private Long hospitalId;

    public CreateAssignmentRequest() {}

    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }

    public Long getHospitalId() { return hospitalId; }
    public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }
}
