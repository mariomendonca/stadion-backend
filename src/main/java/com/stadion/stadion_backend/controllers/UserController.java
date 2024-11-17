package com.stadion.stadion_backend.controllers;

import com.stadion.stadion_backend.domains.dtos.user.UserLoginRequest;
import com.stadion.stadion_backend.domains.dtos.user.UserResponse;
import com.stadion.stadion_backend.domains.entities.User;
import com.stadion.stadion_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(userLoginRequest));
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadImage(file, id);
        return ResponseEntity.ok("Image uploaded successfully");
    }
}
