package com.zoo.application.dto;

public record CreateEnclosureRequest(
    String type,
    double size,
    int maxCapacity
) {}
