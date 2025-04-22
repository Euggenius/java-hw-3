package com.zoo.presentation.controller;

import com.zoo.application.dto.ZooStatisticsDto;
import com.zoo.application.service.ZooStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class ZooStatisticsController {

    private final ZooStatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<ZooStatisticsDto> getStatistics() {
        ZooStatisticsDto stats = statisticsService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
