package com.stadion.stadion_backend.services;

import com.stadion.stadion_backend.domains.dtos.user.UserResponse;
import com.stadion.stadion_backend.domains.entities.User;
import com.stadion.stadion_backend.exceptions.UserAlreadyExistsException;
import com.stadion.stadion_backend.mappers.UserMapper;
import com.stadion.stadion_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(User user) {
        Optional<User> existentUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existentUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this username or email already exists");
        }
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }
}
