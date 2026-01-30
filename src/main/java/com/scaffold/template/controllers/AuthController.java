package com.scaffold.template.controllers;

import com.scaffold.template.dtos.LoginResponseDto;
import com.scaffold.template.dtos.RegisterRequestDto;
import com.scaffold.template.dtos.RegisterResponseDto;
import com.scaffold.template.dtos.RegisterWithGoogleRequestDto;
import com.scaffold.template.models.User;
import com.scaffold.template.services.AuthService;
import com.scaffold.template.services.impl.CustomUserDetailsService;
import com.scaffold.template.services.impl.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.authService = authService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        User registeredUser = authService.registerLocalUser(registerRequestDto.getEmail(), registerRequestDto.getPassword());
        RegisterResponseDto responseDto = new RegisterResponseDto(registeredUser.getId(), registeredUser.getEmail());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/google-register")
    public ResponseEntity<?> registerWithGoogle(@RequestBody RegisterWithGoogleRequestDto registerWithGoogleRequestDto) {
        try {
            User result = authService.registrarConGoogle(registerWithGoogleRequestDto.getTokenId());
            RegisterResponseDto responseDto = new RegisterResponseDto(result.getId(), result.getEmail());
            return ResponseEntity.ok(responseDto); // Devuelve true si el token es válido
        } catch (Exception e) {
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Error desconocido";
            return ResponseEntity.badRequest().body(Map.of("error", errorMsg));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody @Valid RegisterRequestDto loginRequestDto) {
        User user = authService.loginLocalUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponseDto responseDto = new LoginResponseDto(jwtToken);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody RegisterWithGoogleRequestDto loginWithGoogleRequestDto) {
        try {
            User user = authService.registrarConGoogle(loginWithGoogleRequestDto.getTokenId());
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            String jwtToken = jwtService.generateToken(userDetails);
            LoginResponseDto responseDto = new LoginResponseDto(jwtToken);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            String errorMsg = e.getMessage() != null ? e.getMessage() : "Error desconocido";
            return ResponseEntity.badRequest().body(Map.of("error", errorMsg));
        }
    }

}
