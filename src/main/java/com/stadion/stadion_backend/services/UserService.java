package com.stadion.stadion_backend.services;

import com.stadion.stadion_backend.domains.dtos.user.UserLoginRequest;
import com.stadion.stadion_backend.domains.dtos.user.UserResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.domains.entities.User;
import com.stadion.stadion_backend.exceptions.EventNotFoundException;
import com.stadion.stadion_backend.exceptions.UserAlreadyExistsException;
import com.stadion.stadion_backend.exceptions.UserNotFoundException;
import com.stadion.stadion_backend.exceptions.WrongPasswordException;
import com.stadion.stadion_backend.mappers.UserMapper;
import com.stadion.stadion_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    public UserResponse createUser(User user) {
        Optional<User> existentUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existentUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this username or email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    public UserResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.findByUsernameOrEmail(userLoginRequest.getUsername(), userLoginRequest.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        boolean isPasswordCorrect = passwordEncoder.matches(userLoginRequest.getPassword(), user.get().getPassword());
        if (!isPasswordCorrect) {
            throw new WrongPasswordException("Wrong password");
        }


        return userMapper.userToUserResponse(user.get());
    }

    public void uploadImage(MultipartFile file, String id) throws IOException {
        Optional<User> user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " was not found");
        }



        String fileName = "events/" + file.getName() + System.currentTimeMillis();
        String imageUrl = fileUploadService.uploadImage(file, fileName);
        user.get().setImageUrl(imageUrl);

        userRepository.save(user.get());
    }
}
