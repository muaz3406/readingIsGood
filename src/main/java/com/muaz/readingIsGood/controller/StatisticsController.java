package com.muaz.readingIsGood.controller;

import com.muaz.readingIsGood.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/statisticService")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List> getStatisticsByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(statisticsService.getStatisticsByCustomerId(customerId));
    }

}
