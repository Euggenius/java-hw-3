package com.zoo.domain.enclosure.model;

import com.zoo.domain.animal.model.Animal;
import com.zoo.domain.animal.model.AnimalId;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class Enclosure {
    private final EnclosureId id;
    private final EnclosureType type;
    private final double size;
    private final int maxCapacity;
    private final Set<AnimalId> residentAnimalIds;

    public Enclosure(EnclosureId id, EnclosureType type, double size, int maxCapacity) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(type, "Type cannot be null");
         if (size <= 0) throw new IllegalArgumentException("Size must be positive");
         if (maxCapacity <= 0) throw new IllegalArgumentException("Max capacity must be positive");

        this.id = id;
        this.type = type;
        this.size = size;
        this.maxCapacity = maxCapacity;
        this.residentAnimalIds = new HashSet<>();
    }

    public Enclosure(Enclosure other) {
        this.id = other.id;
        this.type = other.type;
        this.size = other.size;
        this.maxCapacity = other.maxCapacity;
        this.residentAnimalIds = new HashSet<>(other.residentAnimalIds);
    }

    public Set<AnimalId> getResidentAnimalIds() {
        return Collections.unmodifiableSet(residentAnimalIds);
    }

    protected Set<AnimalId> getModifiableResidentAnimalIds() {
         return this.residentAnimalIds;
    }

    public boolean addAnimal(Animal animal) {
        Objects.requireNonNull(animal, "Animal cannot be null");
        if (residentAnimalIds.size() >= maxCapacity) {
            System.err.println("Cannot add animal " + animal.getId() + ". Enclosure " + id + " is full.");
             return false;
        }
        if (!type.isCompatible(animal.getSpecies())) {
             System.err.println("Cannot add animal " + animal.getId() + " (" + animal.getSpecies() +
                               "). Incompatible with enclosure type " + type);
             return false;
        }

        if (!getModifiableResidentAnimalIds().add(animal.getId())) {
             System.err.println("Animal " + animal.getId() + " is already in enclosure " + id);
             return false;
        }

        System.out.println("Animal " + animal.getId() + " added to enclosure " + id);
        return true;
    }

    public boolean removeAnimal(AnimalId animalId) {
        Objects.requireNonNull(animalId, "Animal ID cannot be null");
        boolean removed = getModifiableResidentAnimalIds().remove(animalId);
        if (removed) {
             System.out.println("Animal " + animalId + " removed from enclosure " + id);
        } else {
             System.err.println("Animal " + animalId + " not found in enclosure " + id);
        }
        return removed;
    }

    public void clean() {
        System.out.println("Cleaning enclosure " + id + " of type " + type);
    }

    public int getCurrentAnimalCount() {
        return residentAnimalIds.size();
    }

    public boolean isFull() {
        return getCurrentAnimalCount() >= maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enclosure enclosure = (Enclosure) o;
        return Objects.equals(id, enclosure.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
