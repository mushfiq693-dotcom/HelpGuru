package com.helpguru.hospital.repository;

import com.helpguru.hospital.domain.HospitalEntity;
import com.helpguru.hospital.domain.HospitalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
    Optional<HospitalEntity> findByCodeAndIsDeletedFalse(String code);
    List<HospitalEntity> findByRegionIdAndIsDeletedFalse(Long regionId);
    List<HospitalEntity> findByStatusAndIsDeletedFalse(HospitalStatusEnum status);
}
