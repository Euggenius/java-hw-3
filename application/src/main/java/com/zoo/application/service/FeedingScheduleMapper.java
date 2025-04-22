package com.zoo.application.service;

import com.zoo.application.dto.FeedingScheduleDto;
import com.zoo.domain.feeding.model.FeedingSchedule;

class FeedingScheduleMapper {
     public FeedingScheduleDto toDto(FeedingSchedule schedule) {
        return new FeedingScheduleDto(
                schedule.getId().value(),
                schedule.getAnimalId().value(),
                schedule.getFeedingTime().toString(),
                schedule.getFoodType().name(),
                schedule.isDone()
        );
    }
}
