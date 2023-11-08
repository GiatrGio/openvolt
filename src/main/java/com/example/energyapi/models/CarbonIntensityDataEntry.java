package com.example.energyapi.models;

public class CarbonIntensityDataEntry {
    private String from;
    private String to;
    private Intensity intensity;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    public static class Intensity {
        private int forecast;
        private int actual;
        private String index;

        public int getForecast() {
            return forecast;
        }

        public void setForecast(int forecast) {
            this.forecast = forecast;
        }

        public int getActual() {
            return actual;
        }

        public void setActual(int actual) {
            this.actual = actual;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }
}
