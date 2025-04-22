package com.zoo.domain.feeding.model;

import java.util.Objects;
import java.util.UUID;

public record FeedingScheduleId(UUID value) {
     public FeedingScheduleId {
        Objects.requireNonNull(value, "Feeding Schedule ID cannot be null");
    }

    public static FeedingScheduleId generate() {
        return new FeedingScheduleId(UUID.randomUUID());
    }
     public static FeedingScheduleId fromString(String id) {
         try {
             return new FeedingScheduleId(UUID.fromString(id));
         } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException("Invalid UUID format for Feeding Schedule ID: " + id, e);
         }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
