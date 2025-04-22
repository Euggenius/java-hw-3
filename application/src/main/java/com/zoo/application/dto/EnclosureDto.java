package com.zoo.application.dto;

import java.util.UUID;

public record EnclosureDto(
    UUID id,
    String type,
    double size,
    int maxCapacity,
    int currentAnimalCount
) {}
