package com.helpguru.auth.service;

import com.helpguru.auth.dto.AuthResponse;
import com.helpguru.auth.dto.LoginRequest;
import com.helpguru.auth.dto.RegisterRequest;
import com.helpguru.auth.security.JwtTokenProvider;
import com.helpguru.user.domain.RoleEntity;
import com.helpguru.user.domain.RoleEnum;
import com.helpguru.user.domain.UserEntity;
import com.helpguru.user.repository.RoleRepository;
import com.helpguru.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsernameAndIsDeletedFalse(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (userRepository.existsByEmailAndIsDeletedFalse(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        Set<RoleEntity> roles = new HashSet<>();
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            RoleEntity userRole = roleRepository.findByName(RoleEnum.ROLE_RESPONDER)
                    .orElseThrow(() -> new RuntimeException("Default ROLE_RESPONDER not found."));
            roles.add(userRole);
        } else {
            registerRequest.getRoles().forEach(roleStr -> {
                try {
                    RoleEnum roleEnum = RoleEnum.valueOf(roleStr.trim().toUpperCase());
                    RoleEntity role = roleRepository.findByName(roleEnum)
                            .orElseThrow(() -> new RuntimeException("Role " + roleStr + " not found."));
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid role specified: " + roleStr);
                }
            });
        }

        UserEntity user = UserEntity.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .regionId(registerRequest.getRegionId())
                .isActive(true)
                .isDeleted(false)
                .roles(roles)
                .build();

        UserEntity savedUser = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        Set<String> roleNames = savedUser.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toSet());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .roles(roleNames)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        UserEntity user = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> roleNames = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toSet());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(roleNames)
                .build();
    }
}
