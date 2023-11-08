package com.example.energyapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenVoltDataResponse {
    @JsonProperty("startInterval")
    private String startInterval;

    @JsonProperty("endInterval")
    private String endInterval;

    @JsonProperty("granularity")
    private String granularity;

    @JsonProperty("data")
    private List<OpenVoltDataEntry> data;

    public List<OpenVoltDataEntry> getData() {
        return data;
    }

    public void setData(List<OpenVoltDataEntry> data) {
        this.data = data;
    }
}
