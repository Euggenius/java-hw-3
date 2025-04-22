package com.zoo.application.dto;

import com.zoo.domain.animal.model.Gender;
import com.zoo.domain.animal.model.HealthStatus;
import java.time.LocalDate;
import java.util.UUID;

public record AnimalDto(
    UUID id,
    String speciesName,
    boolean speciesIsPredator,
    String name,
    LocalDate birthDate,
    Gender gender,
    String favoriteFoodType,
    HealthStatus healthStatus,
    UUID currentEnclosureId
) {}
