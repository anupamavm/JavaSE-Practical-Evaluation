package com.library.online_library.service.impl;

import com.library.online_library.dto.user.*;
import com.library.online_library.entity.User;
import com.library.online_library.exception.ResourceNotFoundException;
import com.library.online_library.exception.UnAuthorizedUserException;
import com.library.online_library.repository.UserRepository;
import com.library.online_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public Long loginUser(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + userLoginDTO.getEmail() ));

        boolean matches = passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
        if( Boolean.FALSE.equals(matches)){
            throw new UnAuthorizedUserException("Login Fail");
        }
        return user.getId();
    }

    @Override
    public Boolean isAuthenticateUser(Long id) {
        return userRepository.findById(id).isPresent();
    }
}