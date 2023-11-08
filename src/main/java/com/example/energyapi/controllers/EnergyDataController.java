package com.example.energyapi.controllers;

import com.example.energyapi.models.BuildingEnergyDataResponse;
import com.example.energyapi.services.EnergyDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/energyData")
public class EnergyDataController {
    private final EnergyDataService service;

    public EnergyDataController(EnergyDataService service) {
        this.service = service;
    }

    @GetMapping("/building")
    public ResponseEntity<BuildingEnergyDataResponse> getBuildingEnergyData() throws ExecutionException, InterruptedException {
        Future<BuildingEnergyDataResponse> buildingEnergyDataResponse = service.getBuildingEnergyConsumption();

        if (Objects.nonNull(buildingEnergyDataResponse)) {
            return ResponseEntity.status(HttpStatus.OK).body(buildingEnergyDataResponse.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
