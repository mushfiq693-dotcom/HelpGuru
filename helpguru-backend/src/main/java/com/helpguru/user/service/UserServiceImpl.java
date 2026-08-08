package com.helpguru.user.service;

import com.helpguru.user.domain.UserEntity;
import com.helpguru.user.dto.UserDto;
import com.helpguru.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser(String username) {
        UserEntity user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return mapToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> !u.getIsDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToDto(UserEntity user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toSet());

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .regionId(user.getRegionId())
                .isActive(user.getIsActive())
                .roles(roleNames)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
