package com.library.online_library.service;

import com.library.online_library.dto.user.UserLoginDTO;
import com.library.online_library.dto.user.UserRequestDTO;
import com.library.online_library.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO getUserById(Long id);
    Long loginUser(UserLoginDTO userLoginDTO);
}