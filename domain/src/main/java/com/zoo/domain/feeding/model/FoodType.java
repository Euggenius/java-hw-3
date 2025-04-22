package com.zoo.domain.feeding.model;

import java.util.Objects;

public record FoodType(String name) {
    public FoodType {
        Objects.requireNonNull(name, "Food type name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Food type name cannot be blank");
        }
    }
}
