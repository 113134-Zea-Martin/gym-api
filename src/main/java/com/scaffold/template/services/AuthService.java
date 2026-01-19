package com.scaffold.template.services;

import com.scaffold.template.models.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    User registerLocalUser(String email, String password);
}
