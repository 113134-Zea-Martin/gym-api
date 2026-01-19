package com.scaffold.template.entities;

import com.scaffold.template.models.AuthProvider;
import com.scaffold.template.models.ProgressionRule;
import com.scaffold.template.models.WeightUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password; // null si usa Google

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Column
    private String providerId;

    private String name;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit = WeightUnit.KG;

    @Column(precision = 5, scale = 2)
    private BigDecimal weightIncrement = new BigDecimal("2.5");

    @Enumerated(EnumType.STRING)
    private ProgressionRule progressionRule = ProgressionRule.LINEAR;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean enabled = true;

}
