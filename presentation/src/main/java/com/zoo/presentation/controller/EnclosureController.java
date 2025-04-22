package com.zoo.presentation.controller;

import com.zoo.application.dto.*;
import com.zoo.application.service.*;
import com.zoo.domain.enclosure.model.EnclosureId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
public class EnclosureController {

    private final EnclosureService enclosureService;

    @GetMapping("/{id}")
    public ResponseEntity<EnclosureDto> getEnclosureById(@PathVariable String id) {
        EnclosureId enclosureId = EnclosureId.fromString(id);
        EnclosureDto enclosureDto = enclosureService.getEnclosureById(enclosureId);
        return ResponseEntity.ok(enclosureDto);
    }

    @GetMapping
    public ResponseEntity<List<EnclosureDto>> getAllEnclosures() {
        List<EnclosureDto> enclosures = enclosureService.getAllEnclosures();
        return ResponseEntity.ok(enclosures);
    }

    @PostMapping
    public ResponseEntity<EnclosureDto> addEnclosure(@RequestBody CreateEnclosureRequest request, UriComponentsBuilder ucb) {
        EnclosureDto newEnclosure = enclosureService.addEnclosure(request);
         URI locationOfNewEnclosure = ucb.path("/api/enclosures/{id}")
                                      .buildAndExpand(newEnclosure.id())
                                      .toUri();
        return ResponseEntity.created(locationOfNewEnclosure).body(newEnclosure);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnclosure(@PathVariable String id) {
        EnclosureId enclosureId = EnclosureId.fromString(id);
        enclosureService.removeEnclosure(enclosureId);
    }
}
