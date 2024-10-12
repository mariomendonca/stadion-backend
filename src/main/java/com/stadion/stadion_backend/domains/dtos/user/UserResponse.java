package com.stadion.stadion_backend.domains.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String imageUrl;
    private LocalDateTime createdAt;
}
