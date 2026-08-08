package com.helpguru.hospital.service;

import com.helpguru.hospital.dto.CreateHospitalRequest;
import com.helpguru.hospital.dto.HospitalDto;
import com.helpguru.hospital.dto.UpdateHospitalCapacityRequest;

import java.util.List;

public interface HospitalService {
    HospitalDto createHospital(CreateHospitalRequest request);
    HospitalDto getHospitalById(Long id);
    List<HospitalDto> getAllHospitals();
    List<HospitalDto> getHospitalsByRegion(Long regionId);
    HospitalDto updateCapacity(Long id, UpdateHospitalCapacityRequest request);
}
