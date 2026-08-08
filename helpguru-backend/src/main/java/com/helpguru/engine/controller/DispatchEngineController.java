package com.helpguru.engine.controller;

import com.helpguru.engine.dto.*;
import com.helpguru.engine.service.DispatchEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dispatch")
@Tag(name = "Decision Engine & Dispatch Optimization", description = "AI Multi-factor decision engine for optimal emergency unit ranking, ETA calculation & dispatch assignments")
public class DispatchEngineController {

    private final DispatchEngineService dispatchEngineService;

    public DispatchEngineController(DispatchEngineService dispatchEngineService) {
        this.dispatchEngineService = dispatchEngineService;
    }

    @PostMapping("/recommendations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Generate ranked dispatch recommendations for an incident using multi-factor Haversine & capability scoring")
    public ResponseEntity<List<DispatchRecommendationDto>> getDispatchRecommendations(@Valid @RequestBody RecommendationRequest request) {
        List<DispatchRecommendationDto> recommendations = dispatchEngineService.getDispatchRecommendations(request);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/assignments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Execute an emergency dispatch assignment (Dispatches unit to incident location)")
    public ResponseEntity<AssignmentDto> createAssignment(@Valid @RequestBody CreateAssignmentRequest request, Authentication authentication) {
        String username = authentication != null ? authentication.getName() : null;
        AssignmentDto assignment = dispatchEngineService.createAssignment(request, username);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @GetMapping("/assignments/{id}")
    @Operation(summary = "Get emergency dispatch assignment details by ID")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long id) {
        AssignmentDto assignment = dispatchEngineService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("/assignments")
    @Operation(summary = "Get all active emergency dispatch assignments")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments(@RequestParam(required = false) Long incidentId) {
        if (incidentId != null) {
            return ResponseEntity.ok(dispatchEngineService.getAssignmentsByIncident(incidentId));
        }
        return ResponseEntity.ok(dispatchEngineService.getAllAssignments());
    }

    @PatchMapping("/assignments/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('RESPONDER')")
    @Operation(summary = "Update assignment lifecycle status (DISPATCHED, EN_ROUTE, ON_SCENE, COMPLETED)")
    public ResponseEntity<AssignmentDto> updateAssignmentStatus(@PathVariable Long id, @Valid @RequestBody UpdateAssignmentStatusRequest request) {
        AssignmentDto updated = dispatchEngineService.updateAssignmentStatus(id, request);
        return ResponseEntity.ok(updated);
    }
}
