package com.zoo.infrastructure.persistence;

import com.zoo.domain.animal.model.Animal;
import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.animal.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryAnimalRepository implements AnimalRepository {

    private final InMemoryDatabase database;

    @Override
    public Optional<Animal> findById(AnimalId id) {
        return database.findAnimalById(id).map(Animal::new);
    }

    @Override
    public List<Animal> findAll() {
        return database.findAllAnimals().stream()
                .map(Animal::new)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Animal animal) {
        database.saveAnimal(new Animal(animal));
    }

    @Override
    public void deleteById(AnimalId id) {
        database.deleteAnimalById(id);
    }

     @Override
    public long count() {
        return database.countAnimals();
    }
}
