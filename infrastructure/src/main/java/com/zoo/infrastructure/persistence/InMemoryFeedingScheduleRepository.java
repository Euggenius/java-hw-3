package com.zoo.infrastructure.persistence;

import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.feeding.model.FeedingSchedule;
import com.zoo.domain.feeding.model.FeedingScheduleId;
import com.zoo.domain.feeding.repository.FeedingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {

    private final InMemoryDatabase database;

    @Override
    public Optional<FeedingSchedule> findById(FeedingScheduleId id) {
        return database.findScheduleById(id).map(FeedingSchedule::new);
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return database.findAllSchedules().stream()
                .map(FeedingSchedule::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findByAnimalId(AnimalId animalId) {
        return database.findSchedulesByAnimalId(animalId).stream()
                .map(FeedingSchedule::new)
                .collect(Collectors.toList());
    }

    @Override
    public void save(FeedingSchedule schedule) {
        database.saveSchedule(new FeedingSchedule(schedule));
    }

    @Override
    public void deleteById(FeedingScheduleId id) {
        database.deleteScheduleById(id);
    }
}
