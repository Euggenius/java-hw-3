package com.zoo.application.service;

import com.zoo.application.dto.AnimalDto;
import com.zoo.domain.animal.model.Animal;

class AnimalMapper {
    public AnimalDto toDto(Animal animal) {
        return new AnimalDto(
                animal.getId().value(),
                animal.getSpecies().name(),
                animal.getSpecies().isPredator(),
                animal.getName(),
                animal.getBirthDate(),
                animal.getGender(),
                animal.getFavoriteFood().name(),
                animal.getHealthStatus(),
                animal.getCurrentEnclosureId() != null ? animal.getCurrentEnclosureId().value() : null
        );
    }
}
