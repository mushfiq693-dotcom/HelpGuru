package com.helpguru.engine.dto;

import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.resource.dto.ResourceDto;

public class DispatchRecommendationDto {

    private ResourceDto recommendedResource;
    private HospitalDto recommendedHospital;
    private Double score;
    private Double distanceKm;
    private Double estimatedEtaMinutes;
    private String recommendationReason;

    public DispatchRecommendationDto() {}

    public DispatchRecommendationDto(ResourceDto recommendedResource, HospitalDto recommendedHospital, Double score, Double distanceKm, Double estimatedEtaMinutes, String recommendationReason) {
        this.recommendedResource = recommendedResource;
        this.recommendedHospital = recommendedHospital;
        this.score = score;
        this.distanceKm = distanceKm;
        this.estimatedEtaMinutes = estimatedEtaMinutes;
        this.recommendationReason = recommendationReason;
    }

    public ResourceDto getRecommendedResource() { return recommendedResource; }
    public void setRecommendedResource(ResourceDto recommendedResource) { this.recommendedResource = recommendedResource; }

    public HospitalDto getRecommendedHospital() { return recommendedHospital; }
    public void setRecommendedHospital(HospitalDto recommendedHospital) { this.recommendedHospital = recommendedHospital; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getEstimatedEtaMinutes() { return estimatedEtaMinutes; }
    public void setEstimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; }

    public String getRecommendationReason() { return recommendationReason; }
    public void setRecommendationReason(String recommendationReason) { this.recommendationReason = recommendationReason; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private ResourceDto recommendedResource;
        private HospitalDto recommendedHospital;
        private Double score;
        private Double distanceKm;
        private Double estimatedEtaMinutes;
        private String recommendationReason;

        public Builder recommendedResource(ResourceDto recommendedResource) { this.recommendedResource = recommendedResource; return this; }
        public Builder recommendedHospital(HospitalDto recommendedHospital) { this.recommendedHospital = recommendedHospital; return this; }
        public Builder score(Double score) { this.score = score; return this; }
        public Builder distanceKm(Double distanceKm) { this.distanceKm = distanceKm; return this; }
        public Builder estimatedEtaMinutes(Double estimatedEtaMinutes) { this.estimatedEtaMinutes = estimatedEtaMinutes; return this; }
        public Builder recommendationReason(String recommendationReason) { this.recommendationReason = recommendationReason; return this; }

        public DispatchRecommendationDto build() {
            return new DispatchRecommendationDto(recommendedResource, recommendedHospital, score, distanceKm, estimatedEtaMinutes, recommendationReason);
        }
    }
}
