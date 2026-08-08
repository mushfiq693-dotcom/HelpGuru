package com.helpguru.resource.repository;

import com.helpguru.resource.domain.ResourceEntity;
import com.helpguru.resource.domain.ResourceStatusEnum;
import com.helpguru.resource.domain.ResourceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
    Optional<ResourceEntity> findByResourceCodeAndIsDeletedFalse(String resourceCode);
    List<ResourceEntity> findByRegionIdAndIsDeletedFalse(Long regionId);
    List<ResourceEntity> findByStatusAndIsDeletedFalse(ResourceStatusEnum status);
    List<ResourceEntity> findByRegionIdAndResourceTypeAndStatusAndIsDeletedFalse(Long regionId, ResourceTypeEnum resourceType, ResourceStatusEnum status);
}
