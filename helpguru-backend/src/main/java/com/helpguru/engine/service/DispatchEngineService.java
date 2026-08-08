package com.helpguru.engine.service;

import com.helpguru.engine.dto.*;

import java.util.List;

public interface DispatchEngineService {
    List<DispatchRecommendationDto> getDispatchRecommendations(RecommendationRequest request);
    AssignmentDto createAssignment(CreateAssignmentRequest request, String username);
    AssignmentDto getAssignmentById(Long id);
    List<AssignmentDto> getAllAssignments();
    List<AssignmentDto> getAssignmentsByIncident(Long incidentId);
    AssignmentDto updateAssignmentStatus(Long id, UpdateAssignmentStatusRequest request);
}
