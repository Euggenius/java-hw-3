package com.zoo.application.exception;
import com.zoo.domain.enclosure.model.EnclosureId;

public class EnclosureNotFoundException extends ResourceNotFoundException {
    public EnclosureNotFoundException(EnclosureId id) {
        super("Enclosure not found with ID: " + id);
    }
}
