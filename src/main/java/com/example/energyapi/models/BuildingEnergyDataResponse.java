package com.example.energyapi.models;

import java.util.Map;

public class BuildingEnergyDataResponse {
    private double monthlyEnergyConsumption;
    private double monthlyCO2Emissions;
    private Map<FuelType, Double> monthlyFuelMix;

    public BuildingEnergyDataResponse(double monthlyEnergyConsumption, double monthlyCO2Emissions, Map<FuelType, Double> monthlyFuelMix) {
        this.monthlyEnergyConsumption = monthlyEnergyConsumption;
        this.monthlyCO2Emissions = monthlyCO2Emissions;
        this.monthlyFuelMix = monthlyFuelMix;
    }

    public double getMonthlyEnergyConsumption() {
        return monthlyEnergyConsumption;
    }

    public void setMonthlyEnergyConsumption(double monthlyEnergyConsumption) {
        this.monthlyEnergyConsumption = monthlyEnergyConsumption;
    }

    public double getMonthlyCO2Emissions() {
        return monthlyCO2Emissions;
    }

    public void setMonthlyCO2Emissions(double monthlyCO2Emissions) {
        this.monthlyCO2Emissions = monthlyCO2Emissions;
    }

    public Map<FuelType, Double> getMonthlyFuelMix() {
        return monthlyFuelMix;
    }

    public void setMonthlyFuelMix(Map<FuelType, Double> monthlyFuelMix) {
        this.monthlyFuelMix = monthlyFuelMix;
    }
}


