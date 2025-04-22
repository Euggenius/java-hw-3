package com.zoo.presentation.controller;

import com.zoo.application.dto.*;
import com.zoo.application.service.*;
import com.zoo.domain.animal.model.AnimalId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalTransferService animalTransferService;

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable String id) {
        AnimalId animalId = AnimalId.fromString(id);
        AnimalDto animalDto = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animalDto);
    }

    @GetMapping
    public ResponseEntity<List<AnimalDto>> getAllAnimals() {
        List<AnimalDto> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @PostMapping
    public ResponseEntity<AnimalDto> addAnimal(@RequestBody CreateAnimalRequest request, UriComponentsBuilder ucb) {
        AnimalDto newAnimal = animalService.addAnimal(request);
        URI locationOfNewAnimal = ucb.path("/api/animals/{id}")
                                     .buildAndExpand(newAnimal.id())
                                     .toUri();
        return ResponseEntity.created(locationOfNewAnimal).body(newAnimal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable String id) {
        AnimalId animalId = AnimalId.fromString(id);
        AnimalDto animal = animalService.getAnimalById(animalId);

        if (animal.currentEnclosureId() != null) {
            try {
                 animalTransferService.removeAnimalFromEnclosure(animalId);
            } catch (Exception e) {
                 System.err.println("Error removing animal " + animalId + " from enclosure during deletion: " + e.getMessage());
            }
        }
        animalService.removeAnimal(animalId);
    }
}
