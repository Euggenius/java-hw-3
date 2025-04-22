package com.zoo.application.dto;

import com.zoo.domain.animal.model.Gender;
import java.time.LocalDate;

public record CreateAnimalRequest(
    String speciesName,
    boolean speciesIsPredator,
    String name,
    LocalDate birthDate,
    Gender gender,
    String favoriteFoodType
) {}
