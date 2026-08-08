package com.helpguru.user.service;

import com.helpguru.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getCurrentUser(String username);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
}
