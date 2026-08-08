package com.helpguru.incident.service;

import com.helpguru.incident.dto.CreateIncidentRequest;
import com.helpguru.incident.dto.IncidentDto;
import com.helpguru.incident.dto.UpdateIncidentStatusRequest;

import java.util.List;

public interface IncidentService {
    IncidentDto createIncident(CreateIncidentRequest request, String username);
    IncidentDto getIncidentById(Long id);
    List<IncidentDto> getAllIncidents();
    List<IncidentDto> getIncidentsByRegion(Long regionId);
    IncidentDto updateStatus(Long id, UpdateIncidentStatusRequest request);
}
