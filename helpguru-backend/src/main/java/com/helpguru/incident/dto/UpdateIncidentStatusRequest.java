package com.helpguru.incident.dto;

import com.helpguru.incident.domain.IncidentStatusEnum;
import jakarta.validation.constraints.NotNull;

public class UpdateIncidentStatusRequest {

    @NotNull(message = "Incident status is required")
    private IncidentStatusEnum status;

    public UpdateIncidentStatusRequest() {}

    public IncidentStatusEnum getStatus() { return status; }
    public void setStatus(IncidentStatusEnum status) { this.status = status; }
}
