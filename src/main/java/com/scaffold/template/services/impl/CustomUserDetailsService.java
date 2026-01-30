package com.scaffold.template.services.impl;

import com.scaffold.template.entities.UserEntity;
import com.scaffold.template.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Aquí va la lógica para buscar el usuario (por ejemplo, en la base de datos)
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        // Retorna un objeto UserDetails
        UserDetails userDetails = builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Asegúrate de que la contraseña esté codificada
                .roles("USER") // Asigna roles según tu lógica
                .build();
        return userDetails;
    }
}
