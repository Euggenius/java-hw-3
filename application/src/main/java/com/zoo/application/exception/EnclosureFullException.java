package com.zoo.application.exception;
import com.zoo.domain.enclosure.model.EnclosureId;

public class EnclosureFullException extends RuntimeException {
    public EnclosureFullException(EnclosureId id, int capacity) {
        super("Enclosure " + id + " is full (capacity: " + capacity + ")");
    }
}
