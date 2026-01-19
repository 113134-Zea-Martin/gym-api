package com.scaffold.template.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password; // null si usa Google
    private AuthProvider provider;
    private String providerId;
    private String name;
    private WeightUnit weightUnit = WeightUnit.KG;
    private BigDecimal weightIncrement = new BigDecimal("2.5");
    private ProgressionRule progressionRule = ProgressionRule.LINEAR;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled = true;
}
