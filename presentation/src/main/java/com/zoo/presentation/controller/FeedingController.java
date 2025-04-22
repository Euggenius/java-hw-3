package com.zoo.presentation.controller;

import com.zoo.application.dto.*;
import com.zoo.application.service.*;
import com.zoo.domain.animal.model.AnimalId;
import com.zoo.domain.feeding.model.FeedingScheduleId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/feeding-schedule")
@RequiredArgsConstructor
public class FeedingController {

    private final FeedingOrganizationService feedingService;

     @PostMapping
    public ResponseEntity<FeedingScheduleDto> addSchedule(@RequestBody CreateFeedingScheduleRequest request, UriComponentsBuilder ucb) {
        FeedingScheduleDto newSchedule = feedingService.addFeedingSchedule(request);
         URI locationOfNewSchedule = ucb.path("/api/feeding-schedule/{id}")
                                     .buildAndExpand(newSchedule.id())
                                     .toUri();
        return ResponseEntity.created(locationOfNewSchedule).body(newSchedule);
    }

     @GetMapping
    public ResponseEntity<List<FeedingScheduleDto>> getAllSchedules() {
        return ResponseEntity.ok(feedingService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedingScheduleDto> getScheduleById(@PathVariable String id) {
        FeedingScheduleId scheduleId = FeedingScheduleId.fromString(id);
        FeedingScheduleDto scheduleDto = feedingService.getScheduleById(scheduleId);
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<FeedingScheduleDto>> getSchedulesForAnimal(@PathVariable String animalId) {
         AnimalId id = AnimalId.fromString(animalId);
         List<FeedingScheduleDto> schedules = feedingService.getSchedulesForAnimal(id);
         return ResponseEntity.ok(schedules);
    }

    @PostMapping("/{id}/mark-done")
    @ResponseStatus(HttpStatus.OK)
    public void markDone(@PathVariable String id) {
         FeedingScheduleId scheduleId = FeedingScheduleId.fromString(id);
         feedingService.markFeedingDone(scheduleId);
    }

     @DeleteMapping("/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable String id) {
         FeedingScheduleId scheduleId = FeedingScheduleId.fromString(id);
         feedingService.deleteSchedule(scheduleId);
    }
}
