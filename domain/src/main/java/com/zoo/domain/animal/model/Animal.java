package com.zoo.domain.animal.model;

import com.zoo.domain.enclosure.model.EnclosureId;
import com.zoo.domain.feeding.model.FoodType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@ToString
public class Animal {
    private final AnimalId id;
    private final Species species;
    private String name;
    private final LocalDate birthDate;
    private final Gender gender;
    private FoodType favoriteFood;
    private HealthStatus healthStatus;
    private EnclosureId currentEnclosureId;

    public Animal(AnimalId id, Species species, String name, LocalDate birthDate, Gender gender, FoodType favoriteFood) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(species, "Species cannot be null");
        Objects.requireNonNull(birthDate, "Birth date cannot be null");
        Objects.requireNonNull(gender, "Gender cannot be null");
        Objects.requireNonNull(favoriteFood, "Favorite food cannot be null");
        setName(name);

        this.id = id;
        this.species = species;
        this.birthDate = birthDate;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
        this.healthStatus = HealthStatus.HEALTHY;
        this.currentEnclosureId = null;
    }

    public Animal(Animal other) {
        this.id = other.id;
        this.species = other.species;
        this.name = other.name;
        this.birthDate = other.birthDate;
        this.gender = other.gender;
        this.favoriteFood = other.favoriteFood;
        this.healthStatus = other.healthStatus;
        this.currentEnclosureId = other.currentEnclosureId;
    }

    public void feed(FoodType food) {
        System.out.println("Feeding " + name + " the " + species.name() + " with " + food.name());
    }

    public void heal() {
        if (this.healthStatus != HealthStatus.HEALTHY) {
            System.out.println("Healing " + name + " the " + species.name());
            this.healthStatus = HealthStatus.UNDER_TREATMENT;
        } else {
             System.out.println(name + " is already healthy.");
        }
    }

     public void updateHealthStatus(HealthStatus newStatus) {
        Objects.requireNonNull(newStatus, "Health status cannot be null");
        System.out.println("Updating health status for " + name + " to " + newStatus);
        this.healthStatus = newStatus;
     }

    public void assignToEnclosure(EnclosureId newEnclosureId) {
         Objects.requireNonNull(newEnclosureId, "New Enclosure ID cannot be null for assignment");
         System.out.println("Assigning " + name + " to enclosure " + newEnclosureId);
         this.currentEnclosureId = newEnclosureId;
    }

    public void removeFromEnclosure() {
        System.out.println("Removing " + name + " from enclosure " + this.currentEnclosureId);
        this.currentEnclosureId = null;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Animal name cannot be blank");
        }
        this.name = name;
    }

     public void setFavoriteFood(FoodType favoriteFood) {
        Objects.requireNonNull(favoriteFood, "Favorite food cannot be null");
        this.favoriteFood = favoriteFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
