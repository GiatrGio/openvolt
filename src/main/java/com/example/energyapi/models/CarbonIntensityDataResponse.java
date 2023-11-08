package com.example.energyapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CarbonIntensityDataResponse {
    @JsonProperty("data")
    private List<CarbonIntensityDataEntry> data;

    public List<CarbonIntensityDataEntry> getData() {
        return data;
    }

    public void setData(List<CarbonIntensityDataEntry> data) {
        this.data = data;
    }
}

