package com.zoo.application.dto;

import java.util.UUID;

public record FeedingScheduleDto(
    UUID id,
    UUID animalId,
    String feedingTime,
    String foodType,
    boolean done
) {}
