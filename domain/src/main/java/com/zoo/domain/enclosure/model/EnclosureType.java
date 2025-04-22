package com.zoo.domain.enclosure.model;

import com.zoo.domain.animal.model.Species;

public enum EnclosureType {
    PREDATOR_LARGE,
    PREDATOR_SMALL,
    HERBIVORE_LARGE,
    HERBIVORE_SMALL,
    AVIARY,
    AQUARIUM,
    TERRARIUM,
    GENERAL;

    public boolean isCompatible(Species species) {
        return switch (this) {
            case PREDATOR_LARGE, PREDATOR_SMALL -> species.isPredator();
            case HERBIVORE_LARGE, HERBIVORE_SMALL -> !species.isPredator();
            case AVIARY, AQUARIUM, TERRARIUM, GENERAL -> true;
        };
    }
}
