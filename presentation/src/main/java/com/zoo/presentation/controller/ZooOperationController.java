package com.zoo.presentation.controller;

import com.zoo.application.service.*;
import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.enclosure.model.EnclosureId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class ZooOperationController {

    private final AnimalTransferService animalTransferService;

    @PostMapping("/animals/{animalId}/transfer/{targetEnclosureId}")
    @ResponseStatus(HttpStatus.OK)
    public void transferAnimal(
            @PathVariable String animalId,
            @PathVariable String targetEnclosureId) {

        AnimalId animal = AnimalId.fromString(animalId);
        EnclosureId targetEnclosure = EnclosureId.fromString(targetEnclosureId);
        animalTransferService.transferAnimal(animal, targetEnclosure);
    }
}
