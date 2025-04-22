package com.zoo.domain.event;

import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.enclosure.model.EnclosureId;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AnimalMovedEvent {
    private final AnimalId animalId;
    private final EnclosureId sourceEnclosureId;
    private final EnclosureId destinationEnclosureId;
    private final LocalDateTime timestamp;

    public AnimalMovedEvent(AnimalId animalId, EnclosureId sourceEnclosureId, EnclosureId destinationEnclosureId) {
        this.animalId = animalId;
        this.sourceEnclosureId = sourceEnclosureId;
        this.destinationEnclosureId = destinationEnclosureId;
        this.timestamp = LocalDateTime.now();
    }
     @Override
    public String toString() {
        return "AnimalMovedEvent{" +
               "animalId=" + animalId +
               ", sourceEnclosureId=" + sourceEnclosureId +
               ", destinationEnclosureId=" + destinationEnclosureId +
               ", timestamp=" + timestamp +
               '}';
    }
}
