package com.zoo.application.exception;
import com.zoo.domain.animal.model.AnimalId;

public class AnimalNotFoundException extends ResourceNotFoundException {
    public AnimalNotFoundException(AnimalId id) {
        super("Animal not found with ID: " + id);
    }
}
