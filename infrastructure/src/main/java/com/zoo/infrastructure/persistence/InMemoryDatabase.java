package com.zoo.infrastructure.persistence;

import com.zoo.domain.animal.model.Animal;
import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.enclosure.model.Enclosure;
import com.zoo.domain.enclosure.model.EnclosureId;
import com.zoo.domain.feeding.model.FeedingSchedule;
import com.zoo.domain.feeding.model.FeedingScheduleId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class InMemoryDatabase {

    final ConcurrentMap<AnimalId, Animal> animals = new ConcurrentHashMap<>();
    final ConcurrentMap<EnclosureId, Enclosure> enclosures = new ConcurrentHashMap<>();
    final ConcurrentMap<FeedingScheduleId, FeedingSchedule> schedules = new ConcurrentHashMap<>();

    public Optional<Animal> findAnimalById(AnimalId id) {
        return Optional.ofNullable(animals.get(id));
    }

    public List<Animal> findAllAnimals() {
        return List.copyOf(animals.values());
    }

    public void saveAnimal(Animal animal) {
        animals.put(animal.getId(), animal);
    }

    public void deleteAnimalById(AnimalId id) {
        animals.remove(id);
    }

     public long countAnimals() {
        return animals.size();
    }

    public Optional<Enclosure> findEnclosureById(EnclosureId id) {
        return Optional.ofNullable(enclosures.get(id));
    }

    public List<Enclosure> findAllEnclosures() {
        return List.copyOf(enclosures.values());
    }

    public void saveEnclosure(Enclosure enclosure) {
        enclosures.put(enclosure.getId(), enclosure);
    }

    public void deleteEnclosureById(EnclosureId id) {
        enclosures.remove(id);
    }

     public long countEnclosures() {
        return enclosures.size();
    }

     public Optional<FeedingSchedule> findScheduleById(FeedingScheduleId id) {
        return Optional.ofNullable(schedules.get(id));
    }

    public List<FeedingSchedule> findAllSchedules() {
        return List.copyOf(schedules.values());
    }

     public List<FeedingSchedule> findSchedulesByAnimalId(AnimalId animalId) {
        return schedules.values().stream()
                .filter(s -> s.getAnimalId().equals(animalId))
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveSchedule(FeedingSchedule schedule) {
        schedules.put(schedule.getId(), schedule);
    }

    public void deleteScheduleById(FeedingScheduleId id) {
        schedules.remove(id);
    }

    public void clearAll() {
        animals.clear();
        enclosures.clear();
        schedules.clear();
        System.out.println("In-memory database cleared.");
    }
}
