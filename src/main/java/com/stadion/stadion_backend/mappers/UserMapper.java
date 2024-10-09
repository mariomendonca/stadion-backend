package com.stadion.stadion_backend.mappers;

import com.stadion.stadion_backend.domains.dtos.user.UserResponse;
import com.stadion.stadion_backend.domains.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);
}
