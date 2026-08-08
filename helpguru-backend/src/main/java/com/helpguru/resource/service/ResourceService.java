package com.helpguru.resource.service;

import com.helpguru.resource.dto.CreateResourceRequest;
import com.helpguru.resource.dto.ResourceDto;
import com.helpguru.resource.dto.UpdateResourceLocationRequest;

import java.util.List;

public interface ResourceService {
    ResourceDto createResource(CreateResourceRequest request);
    ResourceDto getResourceById(Long id);
    List<ResourceDto> getAllResources();
    List<ResourceDto> getResourcesByRegion(Long regionId);
    ResourceDto updateLocation(Long id, UpdateResourceLocationRequest request);
}
