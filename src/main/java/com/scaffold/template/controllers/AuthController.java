package com.scaffold.template.controllers;

import com.scaffold.template.dtos.RegisterRequestDto;
import com.scaffold.template.dtos.RegisterResponseDto;
import com.scaffold.template.dtos.RegisterWithGoogleRequestDto;
import com.scaffold.template.models.User;
import com.scaffold.template.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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


}
