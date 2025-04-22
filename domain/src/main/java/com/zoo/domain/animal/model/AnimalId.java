package com.zoo.domain.animal.model;

import java.util.Objects;
import java.util.UUID;

public record AnimalId(UUID value) {
    public AnimalId {
        Objects.requireNonNull(value, "Animal ID cannot be null");
    }

    public static AnimalId generate() {
        return new AnimalId(UUID.randomUUID());
    }

    public static AnimalId fromString(String id) {
        try {
            return new AnimalId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
             throw new IllegalArgumentException("Invalid UUID format for Animal ID: " + id, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
