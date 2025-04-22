package com.zoo.application.service;

import com.zoo.application.dto.*;
import com.zoo.application.exception.*;
import com.zoo.domain.enclosure.model.*;
import com.zoo.domain.enclosure.repository.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;
    private final EnclosureMapper enclosureMapper = new EnclosureMapper();

    @Transactional(readOnly = true)
    public EnclosureDto getEnclosureById(EnclosureId id) {
        Enclosure enclosure = enclosureRepository.findById(id)
                .orElseThrow(() -> new EnclosureNotFoundException(id));
        return enclosureMapper.toDto(enclosure);
    }

    @Transactional(readOnly = true)
    public List<EnclosureDto> getAllEnclosures() {
        return enclosureRepository.findAll().stream()
                .map(enclosureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnclosureDto addEnclosure(CreateEnclosureRequest request) {
        EnclosureType type;
        try {
            type = EnclosureType.valueOf(request.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid enclosure type: " + request.type() + ". Valid types are: " + List.of(EnclosureType.values()));
        }

        Enclosure newEnclosure = new Enclosure(
                EnclosureId.generate(),
                type,
                request.size(),
                request.maxCapacity()
        );
        enclosureRepository.save(newEnclosure);
        return enclosureMapper.toDto(newEnclosure);
    }

    @Transactional
    public void removeEnclosure(EnclosureId id) {
         Enclosure enclosure = enclosureRepository.findById(id)
                .orElseThrow(() -> new EnclosureNotFoundException(id));

        if (enclosure.getCurrentAnimalCount() > 0) {
            throw new IllegalStateException("Cannot delete enclosure " + id + ". It contains " + enclosure.getCurrentAnimalCount() + " animals.");
        }
        enclosureRepository.deleteById(id);
         System.out.println("Enclosure " + id + " removed.");
    }
}
