package com.zoo.application.service;

import com.zoo.application.dto.ZooStatisticsDto;
import com.zoo.domain.animal.repository.AnimalRepository;
import com.zoo.domain.enclosure.repository.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZooStatisticsService {

    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    @Transactional(readOnly = true)
    public ZooStatisticsDto getStatistics() {
        long totalAnimals = animalRepository.count();
        long totalEnclosures = enclosureRepository.count();
        long freeEnclosures = enclosureRepository.countFree();
        return new ZooStatisticsDto(totalAnimals, totalEnclosures, freeEnclosures);
    }
}
