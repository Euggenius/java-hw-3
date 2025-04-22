package com.zoo.domain.event;

import com.zoo.domain.feeding.model.FeedingScheduleId;
import com.zoo.domain.animal.model.AnimalId;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class FeedingTimeEvent {
     private final FeedingScheduleId scheduleId;
     private final AnimalId animalId;
     private final LocalTime scheduledTime;
     private final LocalDateTime timestamp;

     public FeedingTimeEvent(FeedingScheduleId scheduleId, AnimalId animalId, LocalTime scheduledTime) {
         this.scheduleId = scheduleId;
         this.animalId = animalId;
         this.scheduledTime = scheduledTime;
         this.timestamp = LocalDateTime.now();
     }
     @Override
    public String toString() {
        return "FeedingTimeEvent{" +
               "scheduleId=" + scheduleId +
               ", animalId=" + animalId +
               ", scheduledTime=" + scheduledTime +
               ", timestamp=" + timestamp +
               '}';
    }
}
