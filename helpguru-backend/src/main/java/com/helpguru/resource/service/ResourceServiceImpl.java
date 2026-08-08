package com.helpguru.resource.service;

import com.helpguru.resource.domain.ResourceEntity;
import com.helpguru.resource.domain.ResourceStatusEnum;
import com.helpguru.resource.dto.CreateResourceRequest;
import com.helpguru.resource.dto.ResourceDto;
import com.helpguru.resource.dto.UpdateResourceLocationRequest;
import com.helpguru.resource.repository.ResourceRepository;
import com.helpguru.resource.telemetry.GpsTrackingEntity;
import com.helpguru.resource.telemetry.GpsTrackingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final GpsTrackingRepository gpsTrackingRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, GpsTrackingRepository gpsTrackingRepository) {
        this.resourceRepository = resourceRepository;
        this.gpsTrackingRepository = gpsTrackingRepository;
    }

    @Override
    @Transactional
    public ResourceDto createResource(CreateResourceRequest request) {
        if (resourceRepository.findByResourceCodeAndIsDeletedFalse(request.getResourceCode()).isPresent()) {
            throw new IllegalArgumentException("Resource code already exists: " + request.getResourceCode());
        }

        ResourceEntity resource = ResourceEntity.builder()
                .resourceCode(request.getResourceCode())
                .name(request.getName())
                .resourceType(request.getResourceType())
                .regionId(request.getRegionId())
                .hospitalId(request.getHospitalId())
                .currentLatitude(request.getCurrentLatitude())
                .currentLongitude(request.getCurrentLongitude())
                .capacity(request.getCapacity())
                .speedKmh(request.getSpeedKmh())
                .status(ResourceStatusEnum.AVAILABLE)
                .isDeleted(false)
                .build();

        ResourceEntity saved = resourceRepository.save(resource);

        // Record initial GPS log
        GpsTrackingEntity gpsLog = new GpsTrackingEntity(
                null,
                saved.getId(),
                saved.getCurrentLatitude(),
                saved.getCurrentLongitude(),
                saved.getSpeedKmh(),
                0.0,
                Instant.now()
        );
        gpsTrackingRepository.save(gpsLog);

        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceDto getResourceById(Long id) {
        ResourceEntity resource = resourceRepository.findById(id)
                .filter(r -> !r.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
        return mapToDto(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDto> getAllResources() {
        return resourceRepository.findAll().stream()
                .filter(r -> !r.getIsDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDto> getResourcesByRegion(Long regionId) {
        return resourceRepository.findByRegionIdAndIsDeletedFalse(regionId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResourceDto updateLocation(Long id, UpdateResourceLocationRequest request) {
        ResourceEntity resource = resourceRepository.findById(id)
                .filter(r -> !r.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));

        resource.setCurrentLatitude(request.getLatitude());
        resource.setCurrentLongitude(request.getLongitude());

        if (request.getSpeedKmh() != null) {
            resource.setSpeedKmh(request.getSpeedKmh());
        }

        if (request.getStatus() != null) {
            resource.setStatus(request.getStatus());
        }

        ResourceEntity updated = resourceRepository.save(resource);

        // Record GPS telemetry log
        GpsTrackingEntity gpsLog = new GpsTrackingEntity(
                null,
                updated.getId(),
                request.getLatitude(),
                request.getLongitude(),
                request.getSpeedKmh() != null ? request.getSpeedKmh() : updated.getSpeedKmh(),
                request.getHeadingDegrees() != null ? request.getHeadingDegrees() : 0.0,
                Instant.now()
        );
        gpsTrackingRepository.save(gpsLog);

        return mapToDto(updated);
    }

    private ResourceDto mapToDto(ResourceEntity r) {
        return ResourceDto.builder()
                .id(r.getId())
                .resourceCode(r.getResourceCode())
                .name(r.getName())
                .resourceType(r.getResourceType())
                .regionId(r.getRegionId())
                .hospitalId(r.getHospitalId())
                .currentLatitude(r.getCurrentLatitude())
                .currentLongitude(r.getCurrentLongitude())
                .status(r.getStatus())
                .capacity(r.getCapacity())
                .speedKmh(r.getSpeedKmh())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
}
