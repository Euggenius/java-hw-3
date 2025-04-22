package com.zoo.domain.feeding.model;

import com.zoo.domain.animal.model.AnimalId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@ToString
public class FeedingSchedule {
    private final FeedingScheduleId id;
    private final AnimalId animalId;
    private LocalTime feedingTime;
    private FoodType foodType;
    private boolean done;

    public FeedingSchedule(FeedingScheduleId id, AnimalId animalId, LocalTime feedingTime, FoodType foodType) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(animalId, "Animal ID cannot be null");
        Objects.requireNonNull(feedingTime, "Feeding time cannot be null");
        Objects.requireNonNull(foodType, "Food type cannot be null");

        this.id = id;
        this.animalId = animalId;
        this.feedingTime = feedingTime;
        this.foodType = foodType;
        this.done = false;
    }

    public FeedingSchedule(FeedingSchedule other) {
        this.id = other.id;
        this.animalId = other.animalId;
        this.feedingTime = other.feedingTime;
        this.foodType = other.foodType;
        this.done = other.done;
    }

    public void updateSchedule(LocalTime newTime, FoodType newFoodType) {
        Objects.requireNonNull(newTime, "New feeding time cannot be null");
        Objects.requireNonNull(newFoodType, "New food type cannot be null");
        this.feedingTime = newTime;
        this.foodType = newFoodType;
        this.done = false;
        System.out.println("Feeding schedule " + id + " updated. New time: " + newTime + ", new food: " + newFoodType);
    }

    public void markAsDone() {
        if (!this.done) {
            this.done = true;
            System.out.println("Feeding schedule " + id + " marked as done.");
        } else {
            System.out.println("Feeding schedule " + id + " was already done.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedingSchedule that = (FeedingSchedule) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
