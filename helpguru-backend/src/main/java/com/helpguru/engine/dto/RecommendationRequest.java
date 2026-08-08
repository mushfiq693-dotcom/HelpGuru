package com.helpguru.engine.dto;

import jakarta.validation.constraints.NotNull;

public class RecommendationRequest {

    @NotNull(message = "Incident ID is required for generating recommendations")
    private Long incidentId;

    private Integer maxResults = 5;

    public RecommendationRequest() {}

    public RecommendationRequest(Long incidentId, Integer maxResults) {
        this.incidentId = incidentId;
        this.maxResults = maxResults != null ? maxResults : 5;
    }

    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }

    public Integer getMaxResults() { return maxResults; }
    public void setMaxResults(Integer maxResults) { this.maxResults = maxResults; }
}
