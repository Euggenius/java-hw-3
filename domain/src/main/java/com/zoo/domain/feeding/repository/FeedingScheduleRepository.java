package com.zoo.domain.feeding.repository;

import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.feeding.model.FeedingSchedule;
import com.zoo.domain.feeding.model.FeedingScheduleId;
import java.util.List;
import java.util.Optional;

public interface FeedingScheduleRepository {
    Optional<FeedingSchedule> findById(FeedingScheduleId id);
    List<FeedingSchedule> findAll();
    List<FeedingSchedule> findByAnimalId(AnimalId animalId);
    void save(FeedingSchedule schedule);
    void deleteById(FeedingScheduleId id);
}
