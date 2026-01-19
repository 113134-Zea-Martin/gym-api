package com.scaffold.template.services.impl;

import com.scaffold.template.entities.UserEntity;
import com.scaffold.template.models.AuthProvider;
import com.scaffold.template.models.User;
import com.scaffold.template.repositories.UserRepository;
import com.scaffold.template.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User registerLocalUser(String email, String password) {
        log.info("Checking if user with email {} already exists", email);
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        log.info("Registering new user with email {}", email);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setProvider(AuthProvider.LOCAL);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setEnabled(true);

        log.info("Mapping User model to UserEntity");
        UserEntity userEntity = modelMapper.map(newUser, UserEntity.class);

        log.info("Saving new user to the database");
        UserEntity savedEntity = userRepository.save(userEntity);

        log.info("Mapping saved UserEntity back to User model");
        newUser = modelMapper.map(savedEntity, User.class);

        return newUser;
    }
}
