package com.zoo.application.service;

import com.zoo.application.dto.*;
import com.zoo.application.exception.*;
import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.animal.repository.AnimalRepository;
import com.zoo.domain.event.DomainEventPublisher;
import com.zoo.domain.event.FeedingTimeEvent;
import com.zoo.domain.feeding.model.*;
import com.zoo.domain.feeding.repository.FeedingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedingOrganizationService {

    private final FeedingScheduleRepository feedingScheduleRepository;
    private final AnimalRepository animalRepository;
    private final DomainEventPublisher eventPublisher;
    private final FeedingScheduleMapper scheduleMapper = new FeedingScheduleMapper();

    @Transactional
    public FeedingScheduleDto addFeedingSchedule(CreateFeedingScheduleRequest request) {
        AnimalId animalId = new AnimalId(request.animalId());
        animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        LocalTime feedingTime;
        try {
             feedingTime = LocalTime.parse(request.feedingTime());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid feeding time format. Expected HH:mm or HH:mm:ss. Received: '" + request.feedingTime() + "'", e);
        }

        FoodType foodType = new FoodType(request.foodType());
        FeedingScheduleId scheduleId = FeedingScheduleId.generate();

        FeedingSchedule newSchedule = new FeedingSchedule(
                scheduleId,
                animalId,
                feedingTime,
                foodType
        );

        feedingScheduleRepository.save(newSchedule);
        return scheduleMapper.toDto(newSchedule);
    }

    @Transactional(readOnly = true)
    public FeedingScheduleDto getScheduleById(FeedingScheduleId scheduleId) {
         return feedingScheduleRepository.findById(scheduleId)
                 .map(scheduleMapper::toDto)
                 .orElseThrow(() -> new FeedingScheduleNotFoundException(scheduleId));
    }

    @Transactional(readOnly = true)
    public List<FeedingScheduleDto> getAllSchedules() {
        return feedingScheduleRepository.findAll().stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FeedingScheduleDto> getSchedulesForAnimal(AnimalId animalId) {
         animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        return feedingScheduleRepository.findByAnimalId(animalId).stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markFeedingDone(FeedingScheduleId scheduleId) {
        FeedingSchedule schedule = feedingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new FeedingScheduleNotFoundException(scheduleId));

        schedule.markAsDone();
        feedingScheduleRepository.save(schedule);
    }

     @Transactional
    public void deleteSchedule(FeedingScheduleId scheduleId) {
        if (feedingScheduleRepository.findById(scheduleId).isEmpty()) {
             throw new FeedingScheduleNotFoundException(scheduleId);
        }
        feedingScheduleRepository.deleteById(scheduleId);
         System.out.println("Feeding schedule " + scheduleId + " deleted.");
    }

     public void triggerFeedingTimeEvents() {
        System.out.println("Checking for due feeding schedules...");
        LocalTime now = LocalTime.now();
        List<FeedingSchedule> dueSchedules = feedingScheduleRepository.findAll().stream()
                .filter(s -> !s.isDone() &&
                             s.getFeedingTime().getHour() == now.getHour() &&
                             s.getFeedingTime().getMinute() == now.getMinute())
                .toList();

        if (!dueSchedules.isEmpty()) {
            System.out.println("Found " + dueSchedules.size() + " due feeding schedules.");
            for (FeedingSchedule schedule : dueSchedules) {
                System.out.println("Publishing FeedingTimeEvent for schedule: " + schedule.getId());
                eventPublisher.publish(new FeedingTimeEvent(schedule.getId(), schedule.getAnimalId(), schedule.getFeedingTime()));
            }
        } else {
             System.out.println("No due feeding schedules found at this minute.");
        }
    }

    private void triggerFeedingTimeEventIfNeeded(FeedingSchedule schedule) {
         LocalTime now = LocalTime.now();
         if (!schedule.isDone() && (schedule.getFeedingTime().equals(now) || schedule.getFeedingTime().isBefore(now))) {
              eventPublisher.publish(new FeedingTimeEvent(schedule.getId(), schedule.getAnimalId(), schedule.getFeedingTime()));
         }
    }
}
