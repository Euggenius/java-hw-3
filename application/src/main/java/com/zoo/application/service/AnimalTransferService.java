package com.zoo.application.service;

import com.zoo.application.exception.*;
import com.zoo.domain.animal.model.*;
import com.zoo.domain.animal.repository.AnimalRepository;
import com.zoo.domain.enclosure.model.*;
import com.zoo.domain.enclosure.repository.EnclosureRepository;
import com.zoo.domain.event.AnimalMovedEvent;
import com.zoo.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnimalTransferService {

    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public void transferAnimal(AnimalId animalId, EnclosureId targetEnclosureId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        Enclosure targetEnclosure = enclosureRepository.findById(targetEnclosureId)
                .orElseThrow(() -> new EnclosureNotFoundException(targetEnclosureId));

        if (targetEnclosureId.equals(animal.getCurrentEnclosureId())) {
            throw new InvalidTransferException("Animal " + animalId + " is already in enclosure " + targetEnclosureId);
        }

        EnclosureId sourceEnclosureId = animal.getCurrentEnclosureId();
        Enclosure sourceEnclosure = null;
        if (sourceEnclosureId != null) {
             sourceEnclosure = enclosureRepository.findById(sourceEnclosureId)
                     .orElseThrow(() ->
                        new IllegalStateException("Inconsistency: Animal " + animalId + " refers to non-existent source enclosure " + sourceEnclosureId)
                     );
        }

        boolean added = targetEnclosure.addAnimal(animal);
        if (!added) {
             if(targetEnclosure.isFull()){
                  throw new EnclosureFullException(targetEnclosureId, targetEnclosure.getMaxCapacity());
             }
             if(!targetEnclosure.getType().isCompatible(animal.getSpecies())){
                 throw new IncompatibleEnclosureTypeException(targetEnclosureId, targetEnclosure.getType(), animal.getSpecies());
             }
             throw new InvalidTransferException("Failed to add animal " + animalId + " to enclosure " + targetEnclosureId + ". Reason unknown or duplicate add attempt.");
        }

        if (sourceEnclosure != null) {
            boolean removed = sourceEnclosure.removeAnimal(animalId);
            if (!removed) {
                 System.err.println("Warning: Animal " + animalId + " was not found in its supposed source enclosure " + sourceEnclosureId + " during transfer.");
            }
            enclosureRepository.save(sourceEnclosure);
        }

        animal.assignToEnclosure(targetEnclosureId);

        animalRepository.save(animal);
        enclosureRepository.save(targetEnclosure);

        eventPublisher.publish(new AnimalMovedEvent(animalId, sourceEnclosureId, targetEnclosureId));

        System.out.println("Successfully transferred animal " + animalId + " from " + (sourceEnclosureId != null ? sourceEnclosureId : "outside") + " to " + targetEnclosureId);
    }

    @Transactional
    public void removeAnimalFromEnclosure(AnimalId animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        EnclosureId sourceEnclosureId = animal.getCurrentEnclosureId();
        if (sourceEnclosureId == null) {
            System.out.println("Animal " + animalId + " is not currently in any enclosure. No removal needed.");
            return;
        }

        Enclosure sourceEnclosure = enclosureRepository.findById(sourceEnclosureId)
                .orElseThrow(() -> new IllegalStateException("Inconsistency: Animal " + animalId + " refers to non-existent source enclosure " + sourceEnclosureId));

        if (sourceEnclosure.removeAnimal(animalId)) {
            animal.removeFromEnclosure();
            animalRepository.save(animal);
            enclosureRepository.save(sourceEnclosure);
            System.out.println("Animal " + animalId + " removed from enclosure " + sourceEnclosureId);
             eventPublisher.publish(new AnimalMovedEvent(animalId, sourceEnclosureId, null));
        } else {
            System.err.println("Warning: Failed to remove animal " + animalId + " from enclosure " + sourceEnclosureId + " where it was expected. Forcing animal state update.");
             animal.removeFromEnclosure();
             animalRepository.save(animal);
        }
    }
}
