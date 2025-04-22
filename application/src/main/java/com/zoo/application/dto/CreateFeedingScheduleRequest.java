package com.zoo.application.dto;

import java.util.UUID;

public record CreateFeedingScheduleRequest(
     UUID animalId,
     String feedingTime,
     String foodType
) {}
