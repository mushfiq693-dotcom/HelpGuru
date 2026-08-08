package com.helpguru.incident.controller;

import com.helpguru.incident.dto.CreateIncidentRequest;
import com.helpguru.incident.dto.IncidentDto;
import com.helpguru.incident.dto.UpdateIncidentStatusRequest;
import com.helpguru.incident.service.IncidentService;
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
@RequestMapping("/api/v1/incidents")
@Tag(name = "Incident Management", description = "Endpoints for reporting, tracking, and prioritizing national disaster incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('RESPONDER')")
    @Operation(summary = "Report a new disaster or emergency incident")
    public ResponseEntity<IncidentDto> createIncident(@Valid @RequestBody CreateIncidentRequest request, Authentication authentication) {
        String username = authentication != null ? authentication.getName() : null;
        IncidentDto created = incidentService.createIncident(request, username);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get emergency incident details by ID")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable Long id) {
        IncidentDto incident = incidentService.getIncidentById(id);
        return ResponseEntity.ok(incident);
    }

    @GetMapping
    @Operation(summary = "Get all emergency incidents (Filterable by region)")
    public ResponseEntity<List<IncidentDto>> getAllIncidents(@RequestParam(required = false) Long regionId) {
        if (regionId != null) {
            return ResponseEntity.ok(incidentService.getIncidentsByRegion(regionId));
        }
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Update incident lifecycle status (REPORTED, EVALUATING, ASSIGNED, RESOLVED)")
    public ResponseEntity<IncidentDto> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateIncidentStatusRequest request) {
        IncidentDto updated = incidentService.updateStatus(id, request);
        return ResponseEntity.ok(updated);
    }
}
