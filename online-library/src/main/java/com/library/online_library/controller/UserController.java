package com.library.online_library.controller;

import com.library.online_library.dto.user.*;
import com.library.online_library.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto ) {
        UserResponseDTO user = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid UserLoginDTO dto , HttpSession session) {
        Long userId = userService.loginUser(dto);
        session.setAttribute("userId", userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

