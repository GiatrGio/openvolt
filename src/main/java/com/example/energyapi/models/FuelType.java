package com.example.energyapi.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

@JsonDeserialize(using = FuelTypeDeserializer.class)
public enum FuelType {
    GAS,
    COAL,
    BIOMASS,
    NUCLEAR,
    HYDRO,
    IMPORTS,
    OTHER,
    WIND,
    SOLAR
}

class FuelTypeDeserializer extends JsonDeserializer<FuelType> {
    @Override
    public FuelType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toUpperCase();
        try {
            return FuelType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid FuelType: " + value);
        }
    }
}
