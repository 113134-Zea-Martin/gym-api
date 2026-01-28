package com.scaffold.template.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
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
import java.util.Collections;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private static final String CLIENT_ID = "408390006930-hj4sfjg9m2lbkr3met53uesuop1q71vn.apps.googleusercontent.com";

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

    @Override
    public User registrarConGoogle(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        // Validar el token
        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Obtener información del usuario
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            if (!emailVerified) {
                throw new Exception("El correo de Google no está verificado.");
            }

            // --- LÓGICA DE TU BACKEND ---
            // 1. Buscar en tu DB si existe un usuario con ese 'email'.
            if (userRepository.findByEmail(email) != null) {
                // Usuario ya existe, proceder a login
                return modelMapper.map(userRepository.findByEmail(email), User.class);
            } else {
                // 2. Si NO existe:
                //    - Crear nuevo usuario en tu DB con ese email y nombre.
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword(passwordEncoder.encode("GOOGLE_OAUTH2_USER")); // Contraseña por defecto o nula
                newUser.setProvider(AuthProvider.GOOGLE);
                newUser.setCreatedAt(LocalDateTime.now());
                newUser.setEnabled(true);
                UserEntity userEntity = modelMapper.map(newUser, UserEntity.class);
                UserEntity savedEntity = userRepository.save(userEntity);
                newUser = modelMapper.map(savedEntity, User.class);
                //    - Marcar que es un usuario de tipo "Social/Google" (opcional).
                // 3. Si YA existe:
                //    - Simplemente loguearlo.
                // 4. Generar tu propio JWT de sesión para el frontend.
                System.out.println("Usuario listo para registro/login: " + email);
                return newUser;
            }

        } else {
            throw new Exception("Token de Google inválido.");
        }
    }

    @Override
    public User loginLocalUser(String email, String password) {
        log.info("Attempting to log in user with email {}", email);
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            log.warn("User with email {} not found", email);
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            log.warn("Invalid password for user with email {}", email);
            throw new IllegalArgumentException("Invalid email or password");
        }

        log.info("User with email {} logged in successfully", email);
        return modelMapper.map(userEntity, User.class);
    }
}
