package com.helpguru.engine.dto;

import com.helpguru.engine.domain.AssignmentStatusEnum;
import jakarta.validation.constraints.NotNull;

public class UpdateAssignmentStatusRequest {

    @NotNull(message = "Assignment status is required")
    private AssignmentStatusEnum status;

    public UpdateAssignmentStatusRequest() {}

    public AssignmentStatusEnum getStatus() { return status; }
    public void setStatus(AssignmentStatusEnum status) { this.status = status; }
}
