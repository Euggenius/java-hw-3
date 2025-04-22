package com.zoo.domain.animal.model;

import java.util.Objects;

public record Species(String name, boolean isPredator) {
    public Species {
        Objects.requireNonNull(name, "Species name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Species name cannot be blank");
        }
    }
}
