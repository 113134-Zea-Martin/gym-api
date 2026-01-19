package com.scaffold.template.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @Email(message = "Email should be a valid email address")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
