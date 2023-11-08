package com.example.energyapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GenerationDataResponse {

    @JsonProperty("data")
    private List<GenerationDataEntry> data;

    public List<GenerationDataEntry> getData() {
        return data;
    }

    public void setData(List<GenerationDataEntry> data) {
        this.data = data;
    }
}
