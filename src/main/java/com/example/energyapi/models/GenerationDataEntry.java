package com.example.energyapi.models;

import java.util.List;

public class GenerationDataEntry {
    private String from;
    private String to;
    private List<GenerationMix> generationmix;

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

    public List<GenerationMix> getGenerationmix() {
        return generationmix;
    }

    public void setGenerationmix(List<GenerationMix> generationmix) {
        this.generationmix = generationmix;
    }
}

