package com.zoo.domain.enclosure.model;

import java.util.Objects;
import java.util.UUID;

public record EnclosureId(UUID value) {
    public EnclosureId {
        Objects.requireNonNull(value, "Enclosure ID cannot be null");
    }

    public static EnclosureId generate() {
        return new EnclosureId(UUID.randomUUID());
    }
     public static EnclosureId fromString(String id) {
         try {
             return new EnclosureId(UUID.fromString(id));
         } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException("Invalid UUID format for Enclosure ID: " + id, e);
         }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
