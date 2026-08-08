package com.helpguru.incident.service;

import com.helpguru.incident.domain.IncidentEntity;
import com.helpguru.incident.domain.IncidentStatusEnum;
import com.helpguru.incident.dto.CreateIncidentRequest;
import com.helpguru.incident.dto.IncidentDto;
import com.helpguru.incident.dto.UpdateIncidentStatusRequest;
import com.helpguru.incident.repository.IncidentRepository;
import com.helpguru.user.domain.UserEntity;
import com.helpguru.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public IncidentDto createIncident(CreateIncidentRequest request, String username) {
        Long reportedByUserId = null;
        if (username != null) {
            reportedByUserId = userRepository.findByUsernameAndIsDeletedFalse(username)
                    .map(UserEntity::getId)
                    .orElse(null);
        }

        String incidentCode = "INC-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        IncidentEntity incident = IncidentEntity.builder()
                .incidentCode(incidentCode)
                .title(request.getTitle())
                .description(request.getDescription())
                .severityLevel(request.getSeverityLevel())
                .affectedPeopleCount(request.getAffectedPeopleCount())
                .timeSensitivityLevel(request.getTimeSensitivityLevel())
                .requiredResourceType(request.getRequiredResourceType())
                .regionId(request.getRegionId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .addressText(request.getAddressText())
                .status(IncidentStatusEnum.REPORTED)
                .reportedByUserId(reportedByUserId)
                .isDeleted(false)
                .build();

        IncidentEntity saved = incidentRepository.save(incident);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public IncidentDto getIncidentById(Long id) {
        IncidentEntity incident = incidentRepository.findById(id)
                .filter(i -> !i.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
        return mapToDto(incident);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidentDto> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .filter(i -> !i.getIsDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidentDto> getIncidentsByRegion(Long regionId) {
        return incidentRepository.findByRegionIdAndIsDeletedFalse(regionId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IncidentDto updateStatus(Long id, UpdateIncidentStatusRequest request) {
        IncidentEntity incident = incidentRepository.findById(id)
                .filter(i -> !i.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));

        incident.setStatus(request.getStatus());
        IncidentEntity updated = incidentRepository.save(incident);
        return mapToDto(updated);
    }

    private IncidentDto mapToDto(IncidentEntity i) {
        return IncidentDto.builder()
                .id(i.getId())
                .incidentCode(i.getIncidentCode())
                .title(i.getTitle())
                .description(i.getDescription())
                .severityLevel(i.getSeverityLevel())
                .affectedPeopleCount(i.getAffectedPeopleCount())
                .timeSensitivityLevel(i.getTimeSensitivityLevel())
                .requiredResourceType(i.getRequiredResourceType())
                .regionId(i.getRegionId())
                .latitude(i.getLatitude())
                .longitude(i.getLongitude())
                .addressText(i.getAddressText())
                .status(i.getStatus())
                .reportedByUserId(i.getReportedByUserId())
                .createdAt(i.getCreatedAt())
                .updatedAt(i.getUpdatedAt())
                .build();
    }
}
