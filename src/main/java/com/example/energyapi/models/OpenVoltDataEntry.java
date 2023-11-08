package com.example.energyapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenVoltDataEntry {
    @JsonProperty("start_interval")
    private String startInterval;

    @JsonProperty("meter_id")
    private String meterId;

    @JsonProperty("meter_number")
    private String meterNumber;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("consumption")
    private String consumption;

    @JsonProperty("consumption_units")
    private String consumptionUnits;

    public String getStartInterval() {
        return startInterval;
    }

    public void setStartInterval(String startInterval) {
        this.startInterval = startInterval;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getConsumptionUnits() {
        return consumptionUnits;
    }

    public void setConsumptionUnits(String consumptionUnits) {
        this.consumptionUnits = consumptionUnits;
    }
}