package com.zoo.application.exception;
import com.zoo.domain.feeding.model.FeedingScheduleId;

public class FeedingScheduleNotFoundException extends ResourceNotFoundException {
    public FeedingScheduleNotFoundException(FeedingScheduleId id) {
        super("Feeding schedule not found with ID: " + id);
    }
     public FeedingScheduleNotFoundException(String message) {
        super(message);
    }
}
