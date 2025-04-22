package com.zoo.application.service;

import com.zoo.application.dto.*;
import com.zoo.application.exception.*;
import com.zoo.domain.animal.model.*;
import com.zoo.domain.animal.repository.AnimalRepository;
import com.zoo.domain.feeding.model.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper = new AnimalMapper();

    @Transactional(readOnly = true)
    public AnimalDto getAnimalById(AnimalId id) {
        return animalRepository.findById(id)
                .map(animalMapper::toDto)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<AnimalDto> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnimalDto addAnimal(CreateAnimalRequest request) {
        Species species = new Species(request.speciesName(), request.speciesIsPredator());
        FoodType foodType = new FoodType(request.favoriteFoodType());
        AnimalId newId = AnimalId.generate();

        Animal newAnimal = new Animal(
                newId,
                species,
                request.name(),
                request.birthDate(),
                request.gender(),
                foodType
        );
        animalRepository.save(newAnimal);
        return animalMapper.toDto(newAnimal);
    }

    @Transactional
    public void removeAnimal(AnimalId id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));

        if (animal.getCurrentEnclosureId() != null) {
             throw new IllegalStateException("Cannot delete animal " + id + ". It is still assigned to enclosure " + animal.getCurrentEnclosureId() + ". Use the transfer service to remove it first.");
        }

        animalRepository.deleteById(id);
        System.out.println("Animal " + id + " removed.");
    }
}
