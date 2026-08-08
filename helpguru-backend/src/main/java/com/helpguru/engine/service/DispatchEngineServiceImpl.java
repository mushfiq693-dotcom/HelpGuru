package com.helpguru.engine.service;

import com.helpguru.engine.domain.AssignmentEntity;
import com.helpguru.engine.domain.AssignmentStatusEnum;
import com.helpguru.engine.dto.*;
import com.helpguru.engine.repository.AssignmentRepository;
import com.helpguru.hospital.domain.HospitalEntity;
import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.hospital.repository.HospitalRepository;
import com.helpguru.hospital.service.HospitalService;
import com.helpguru.incident.domain.IncidentEntity;
import com.helpguru.incident.domain.IncidentStatusEnum;
import com.helpguru.incident.dto.IncidentDto;
import com.helpguru.incident.repository.IncidentRepository;
import com.helpguru.incident.service.IncidentService;
import com.helpguru.resource.domain.ResourceEntity;
import com.helpguru.resource.domain.ResourceStatusEnum;
import com.helpguru.resource.dto.ResourceDto;
import com.helpguru.resource.repository.ResourceRepository;
import com.helpguru.resource.service.ResourceService;
import com.helpguru.user.domain.UserEntity;
import com.helpguru.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DispatchEngineServiceImpl implements DispatchEngineService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final IncidentRepository incidentRepository;
    private final ResourceRepository resourceRepository;
    private final HospitalRepository hospitalRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final IncidentService incidentService;
    private final ResourceService resourceService;
    private final HospitalService hospitalService;

    public DispatchEngineServiceImpl(IncidentRepository incidentRepository,
                                     ResourceRepository resourceRepository,
                                     HospitalRepository hospitalRepository,
                                     AssignmentRepository assignmentRepository,
                                     UserRepository userRepository,
                                     IncidentService incidentService,
                                     ResourceService resourceService,
                                     HospitalService hospitalService) {
        this.incidentRepository = incidentRepository;
        this.resourceRepository = resourceRepository;
        this.hospitalRepository = hospitalRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.incidentService = incidentService;
        this.resourceService = resourceService;
        this.hospitalService = hospitalService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DispatchRecommendationDto> getDispatchRecommendations(RecommendationRequest request) {
        IncidentEntity incident = incidentRepository.findById(request.getIncidentId())
                .filter(i -> !i.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + request.getIncidentId()));

        // Fetch candidate responder resources in region
        List<ResourceEntity> candidates = resourceRepository.findByRegionIdAndIsDeletedFalse(incident.getRegionId());

        // If no candidate in region, fallback to all active resources
        if (candidates.isEmpty()) {
            candidates = resourceRepository.findAll().stream()
                    .filter(r -> !r.getIsDeleted() && r.getStatus() == ResourceStatusEnum.AVAILABLE)
                    .collect(Collectors.toList());
        }

        // Fetch operational hospitals in region
        List<HospitalEntity> hospitals = hospitalRepository.findByRegionIdAndIsDeletedFalse(incident.getRegionId());

        List<DispatchRecommendationDto> recommendations = new ArrayList<>();

        for (ResourceEntity resource : candidates) {
            double distanceKm = calculateHaversineDistance(
                    incident.getLatitude(), incident.getLongitude(),
                    resource.getCurrentLatitude(), resource.getCurrentLongitude()
            );

            double speed = resource.getSpeedKmh() > 0 ? resource.getSpeedKmh() : 60.0;
            double etaMinutes = (distanceKm / speed) * 60.0;

            // Score computation
            double proximityScore = Math.max(0.0, 50.0 - (distanceKm * 2.0));
            double availabilityScore = (resource.getStatus() == ResourceStatusEnum.AVAILABLE) ? 30.0 : 10.0;
            double resourceTypeMatchScore = (resource.getResourceType() == incident.getRequiredResourceType()) ? 20.0 : 5.0;

            double totalScore = proximityScore + availabilityScore + resourceTypeMatchScore;

            // Find closest available hospital
            HospitalEntity bestHospital = null;
            double minHospitalDistance = Double.MAX_VALUE;
            for (HospitalEntity h : hospitals) {
                double hDist = calculateHaversineDistance(
                        incident.getLatitude(), incident.getLongitude(),
                        h.getLatitude(), h.getLongitude()
                );
                if (hDist < minHospitalDistance) {
                    minHospitalDistance = hDist;
                    bestHospital = h;
                }
            }

            ResourceDto resourceDto = resourceService.getResourceById(resource.getId());
            HospitalDto hospitalDto = bestHospital != null ? hospitalService.getHospitalById(bestHospital.getId()) : null;

            String reason = String.format("Proximity: %.1f km, Estimated ETA: %.1f mins, Type Match: %s",
                    distanceKm, etaMinutes, resource.getResourceType());

            DispatchRecommendationDto rec = DispatchRecommendationDto.builder()
                    .recommendedResource(resourceDto)
                    .recommendedHospital(hospitalDto)
                    .score(Math.round(totalScore * 10.0) / 10.0)
                    .distanceKm(Math.round(distanceKm * 10.0) / 10.0)
                    .estimatedEtaMinutes(Math.round(etaMinutes * 10.0) / 10.0)
                    .recommendationReason(reason)
                    .build();

            recommendations.add(rec);
        }

        // Rank highest score first
        recommendations.sort(Comparator.comparing(DispatchRecommendationDto::getScore).reversed());

        int limit = Math.min(request.getMaxResults(), recommendations.size());
        return recommendations.subList(0, limit);
    }

    @Override
    @Transactional
    public AssignmentDto createAssignment(CreateAssignmentRequest request, String username) {
        IncidentEntity incident = incidentRepository.findById(request.getIncidentId())
                .filter(i -> !i.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + request.getIncidentId()));

        ResourceEntity resource = resourceRepository.findById(request.getResourceId())
                .filter(r -> !r.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + request.getResourceId()));

        Long assignedByUserId = null;
        if (username != null) {
            assignedByUserId = userRepository.findByUsernameAndIsDeletedFalse(username)
                    .map(UserEntity::getId)
                    .orElse(null);
        }

        String assignmentCode = "ASN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        double distanceKm = calculateHaversineDistance(
                incident.getLatitude(), incident.getLongitude(),
                resource.getCurrentLatitude(), resource.getCurrentLongitude()
        );
        double etaMinutes = (distanceKm / (resource.getSpeedKmh() > 0 ? resource.getSpeedKmh() : 60.0)) * 60.0;
        double score = Math.max(0.0, 100.0 - (distanceKm * 2.0));

        AssignmentEntity assignment = AssignmentEntity.builder()
                .assignmentCode(assignmentCode)
                .incidentId(request.getIncidentId())
                .resourceId(request.getResourceId())
                .hospitalId(request.getHospitalId())
                .calculatedScore(Math.round(score * 10.0) / 10.0)
                .estimatedDistanceKm(Math.round(distanceKm * 10.0) / 10.0)
                .estimatedEtaMinutes(Math.round(etaMinutes * 10.0) / 10.0)
                .assignedByUserId(assignedByUserId)
                .status(AssignmentStatusEnum.DISPATCHED)
                .isDeleted(false)
                .build();

        AssignmentEntity saved = assignmentRepository.save(assignment);

        // Update resource status to DISPATCHED
        resource.setStatus(ResourceStatusEnum.DISPATCHED);
        resourceRepository.save(resource);

        // Update incident status to ASSIGNED
        incident.setStatus(IncidentStatusEnum.ASSIGNED);
        incidentRepository.save(incident);

        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentDto getAssignmentById(Long id) {
        AssignmentEntity assignment = assignmentRepository.findById(id)
                .filter(a -> !a.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        return mapToDto(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .filter(a -> !a.getIsDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDto> getAssignmentsByIncident(Long incidentId) {
        return assignmentRepository.findByIncidentIdAndIsDeletedFalse(incidentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AssignmentDto updateAssignmentStatus(Long id, UpdateAssignmentStatusRequest request) {
        AssignmentEntity assignment = assignmentRepository.findById(id)
                .filter(a -> !a.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));

        assignment.setStatus(request.getStatus());

        // Sync resource status when assignment completes or cancels
        if (request.getStatus() == AssignmentStatusEnum.COMPLETED || request.getStatus() == AssignmentStatusEnum.CANCELLED) {
            resourceRepository.findById(assignment.getResourceId()).ifPresent(res -> {
                res.setStatus(ResourceStatusEnum.AVAILABLE);
                resourceRepository.save(res);
            });
            incidentRepository.findById(assignment.getIncidentId()).ifPresent(inc -> {
                if (request.getStatus() == AssignmentStatusEnum.COMPLETED) {
                    inc.setStatus(IncidentStatusEnum.RESOLVED);
                }
                incidentRepository.save(inc);
            });
        }

        AssignmentEntity updated = assignmentRepository.save(assignment);
        return mapToDto(updated);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private AssignmentDto mapToDto(AssignmentEntity a) {
        IncidentDto incident = incidentService.getIncidentById(a.getIncidentId());
        ResourceDto resource = resourceService.getResourceById(a.getResourceId());
        HospitalDto hospital = a.getHospitalId() != null ? hospitalService.getHospitalById(a.getHospitalId()) : null;

        return AssignmentDto.builder()
                .id(a.getId())
                .assignmentCode(a.getAssignmentCode())
                .incident(incident)
                .resource(resource)
                .hospital(hospital)
                .calculatedScore(a.getCalculatedScore())
                .estimatedEtaMinutes(a.getEstimatedEtaMinutes())
                .assignedByUserId(a.getAssignedByUserId())
                .status(a.getStatus())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }
}
