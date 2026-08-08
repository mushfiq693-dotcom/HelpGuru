package com.helpguru.resource.controller;

import com.helpguru.resource.dto.CreateResourceRequest;
import com.helpguru.resource.dto.ResourceDto;
import com.helpguru.resource.dto.UpdateResourceLocationRequest;
import com.helpguru.resource.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
@Tag(name = "Resource Management", description = "Endpoints for emergency vehicles, responder units, and live GPS tracking")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Register a new emergency responder resource (Ambulance, Rescue Team, Helicopter, etc.)")
    public ResponseEntity<ResourceDto> createResource(@Valid @RequestBody CreateResourceRequest request) {
        ResourceDto created = resourceService.createResource(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get emergency resource details by ID")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable Long id) {
        ResourceDto resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @Operation(summary = "Get all emergency resources (Filterable by region)")
    public ResponseEntity<List<ResourceDto>> getAllResources(@RequestParam(required = false) Long regionId) {
        if (regionId != null) {
            return ResponseEntity.ok(resourceService.getResourcesByRegion(regionId));
        }
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PatchMapping("/{id}/location")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('RESPONDER')")
    @Operation(summary = "Update live GPS location telemetry & operational status")
    public ResponseEntity<ResourceDto> updateLocation(@PathVariable Long id, @Valid @RequestBody UpdateResourceLocationRequest request) {
        ResourceDto updated = resourceService.updateLocation(id, request);
        return ResponseEntity.ok(updated);
    }
}
