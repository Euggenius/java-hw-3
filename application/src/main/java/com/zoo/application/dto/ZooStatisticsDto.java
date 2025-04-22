package com.zoo.application.dto;

public record ZooStatisticsDto(
    long totalAnimals,
    long totalEnclosures,
    long freeEnclosures
) {}
