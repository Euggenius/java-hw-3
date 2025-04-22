package com.zoo.application.exception;
import com.zoo.domain.animal.model.Species;
import com.zoo.domain.enclosure.model.EnclosureId;
import com.zoo.domain.enclosure.model.EnclosureType;

public class IncompatibleEnclosureTypeException extends RuntimeException {
    public IncompatibleEnclosureTypeException(EnclosureId enclosureId, EnclosureType enclosureType, Species animalSpecies) {
        super("Cannot add animal of species " + animalSpecies.name() +
              " to enclosure " + enclosureId + " of type " + enclosureType);
    }
}
