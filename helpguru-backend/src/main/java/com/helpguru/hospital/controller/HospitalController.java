package com.helpguru.hospital.controller;

import com.helpguru.hospital.dto.CreateHospitalRequest;
import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.hospital.dto.UpdateHospitalCapacityRequest;
import com.helpguru.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@Tag(name = "Hospital Management", description = "Endpoints for hospital capacity tracking and emergency bed management")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('HOSPITAL_ADMIN')")
    @Operation(summary = "Register a new hospital facility (Admin, Operator, or Hospital Admin only)")
    public ResponseEntity<HospitalDto> createHospital(@Valid @RequestBody CreateHospitalRequest request) {
        HospitalDto created = hospitalService.createHospital(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hospital details by ID")
    public ResponseEntity<HospitalDto> getHospitalById(@PathVariable Long id) {
        HospitalDto hospital = hospitalService.getHospitalById(id);
        return ResponseEntity.ok(hospital);
    }

    @GetMapping
    @Operation(summary = "Get all operational hospitals")
    public ResponseEntity<List<HospitalDto>> getAllHospitals(@RequestParam(required = false) Long regionId) {
        if (regionId != null) {
            return ResponseEntity.ok(hospitalService.getHospitalsByRegion(regionId));
        }
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @PatchMapping("/{id}/capacity")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('HOSPITAL_ADMIN')")
    @Operation(summary = "Update available bed and ICU capacity in real-time")
    public ResponseEntity<HospitalDto> updateCapacity(@PathVariable Long id, @Valid @RequestBody UpdateHospitalCapacityRequest request) {
        HospitalDto updated = hospitalService.updateCapacity(id, request);
        return ResponseEntity.ok(updated);
    }
}
