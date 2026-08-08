package com.helpguru.hospital.service;

import com.helpguru.hospital.domain.HospitalEntity;
import com.helpguru.hospital.domain.HospitalStatusEnum;
import com.helpguru.hospital.dto.CreateHospitalRequest;
import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.hospital.dto.UpdateHospitalCapacityRequest;
import com.helpguru.hospital.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    @Transactional
    public HospitalDto createHospital(CreateHospitalRequest request) {
        if (hospitalRepository.findByCodeAndIsDeletedFalse(request.getCode()).isPresent()) {
            throw new IllegalArgumentException("Hospital code already exists: " + request.getCode());
        }

        HospitalEntity hospital = HospitalEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .regionId(request.getRegionId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .totalBeds(request.getTotalBeds())
                .availableBeds(request.getAvailableBeds())
                .icuTotal(request.getIcuTotal())
                .icuAvailable(request.getIcuAvailable())
                .emergencyContact(request.getEmergencyContact())
                .status(HospitalStatusEnum.OPERATIONAL)
                .isDeleted(false)
                .build();

        HospitalEntity saved = hospitalRepository.save(hospital);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public HospitalDto getHospitalById(Long id) {
        HospitalEntity hospital = hospitalRepository.findById(id)
                .filter(h -> !h.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
        return mapToDto(hospital);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalDto> getAllHospitals() {
        return hospitalRepository.findAll().stream()
                .filter(h -> !h.getIsDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalDto> getHospitalsByRegion(Long regionId) {
        return hospitalRepository.findByRegionIdAndIsDeletedFalse(regionId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HospitalDto updateCapacity(Long id, UpdateHospitalCapacityRequest request) {
        HospitalEntity hospital = hospitalRepository.findById(id)
                .filter(h -> !h.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

        hospital.setAvailableBeds(request.getAvailableBeds());
        hospital.setIcuAvailable(request.getIcuAvailable());

        if (request.getStatus() != null) {
            hospital.setStatus(request.getStatus());
        } else if (request.getAvailableBeds() <= 0) {
            hospital.setStatus(HospitalStatusEnum.FULL_CAPACITY);
        } else {
            hospital.setStatus(HospitalStatusEnum.OPERATIONAL);
        }

        HospitalEntity updated = hospitalRepository.save(hospital);
        return mapToDto(updated);
    }

    private HospitalDto mapToDto(HospitalEntity h) {
        return HospitalDto.builder()
                .id(h.getId())
                .name(h.getName())
                .code(h.getCode())
                .regionId(h.getRegionId())
                .latitude(h.getLatitude())
                .longitude(h.getLongitude())
                .totalBeds(h.getTotalBeds())
                .availableBeds(h.getAvailableBeds())
                .icuTotal(h.getIcuTotal())
                .icuAvailable(h.getIcuAvailable())
                .emergencyContact(h.getEmergencyContact())
                .status(h.getStatus())
                .createdAt(h.getCreatedAt())
                .updatedAt(h.getUpdatedAt())
                .build();
    }
}
