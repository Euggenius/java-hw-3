package com.zoo.domain.event;

public interface DomainEventPublisher {
    void publish(Object event);
}
