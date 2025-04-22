package com.zoo.domain.animal.repository;

import com.zoo.domain.animal.model.Animal;
import com.zoo.domain.animal.model.AnimalId;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {
    Optional<Animal> findById(AnimalId id);
    List<Animal> findAll();
    void save(Animal animal);
    void deleteById(AnimalId id);
    long count();
}
