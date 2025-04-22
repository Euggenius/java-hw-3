package com.zoo.application.service;

import com.zoo.application.dto.EnclosureDto;
import com.zoo.domain.enclosure.model.Enclosure;

class EnclosureMapper {
     public EnclosureDto toDto(Enclosure enclosure) {
        return new EnclosureDto(
                enclosure.getId().value(),
                enclosure.getType().name(),
                enclosure.getSize(),
                enclosure.getMaxCapacity(),
                enclosure.getCurrentAnimalCount()
        );
    }
}
