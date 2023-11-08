package com.example.energyapi.services;

import com.example.energyapi.models.BuildingEnergyDataResponse;
import com.example.energyapi.models.CarbonIntensityDataEntry;
import com.example.energyapi.models.CarbonIntensityDataResponse;
import com.example.energyapi.models.FuelType;
import com.example.energyapi.models.GenerationDataEntry;
import com.example.energyapi.models.GenerationDataResponse;
import com.example.energyapi.models.GenerationMix;
import com.example.energyapi.models.OpenVoltDataEntry;
import com.example.energyapi.models.OpenVoltDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.Double.parseDouble;

@Service
public class EnergyDataService {

    @Value("${OPENVOLT_KEY}")
    private String OPENVOLT_KEY;

    private static final Logger logger = LoggerFactory.getLogger(EnergyDataService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public Future<BuildingEnergyDataResponse> getBuildingEnergyConsumption() {
        logger.info("Start request");
        OpenVoltDataResponse monthlyEnergyConsumption = getOpenVoltData();

        CarbonIntensityDataResponse monthlyCarbonIntensity = getCarbonIntensityData();

        GenerationDataResponse generationMixDataResponse = getGenerationMixData();

        BuildingEnergyDataResponse buildingEnergyDataResponse = calculateEnergyDataResponse(
                monthlyEnergyConsumption.getData(), monthlyCarbonIntensity.getData(),
                generationMixDataResponse.getData());

        logger.info("Finish request");
        return CompletableFuture.completedFuture(buildingEnergyDataResponse);
    }

    private BuildingEnergyDataResponse calculateEnergyDataResponse(
            List<OpenVoltDataEntry> energyConsumptionData, List<CarbonIntensityDataEntry> carbonIntensityData,
            List<GenerationDataEntry> generationMixDataList)
    {
        double monthlyEnergyConsumption = 0.0;
        double monthlyCO2Emissions = 0.0;
        Map<FuelType, Double> monthlyGenerationMixSumPerFuelType = getInitialGenerationMixMap();

        energyConsumptionData.remove(energyConsumptionData.size() - 1);
        for (int i = 0; i < energyConsumptionData.size(); i++) {
            double energyConsumption = parseDouble(energyConsumptionData.get(i).getConsumption());
            int co2Intensity = carbonIntensityData.get(i).getIntensity().getActual();
            double co2Emissions = energyConsumption * co2Intensity;
            GenerationDataEntry generationDataEntry = generationMixDataList.get(i);

            monthlyEnergyConsumption += energyConsumption;
            monthlyCO2Emissions += co2Emissions;
            addEntryToMonthlyGenerationMix(generationDataEntry, monthlyGenerationMixSumPerFuelType);
        }

        Map<FuelType, Double> monthlyGenerationMixPercentage =
                calculateFinalPercentageForEachFuelType(monthlyGenerationMixSumPerFuelType);
        double monthlyCO2EmissionsKg = monthlyCO2Emissions / 1000;

        return new BuildingEnergyDataResponse(monthlyEnergyConsumption, monthlyCO2EmissionsKg, monthlyGenerationMixPercentage);
    }

    private Map<FuelType, Double> calculateFinalPercentageForEachFuelType(Map<FuelType, Double> monthlyGenerationMixSumPerFuelType) {
        double totalSumOfPercentages = monthlyGenerationMixSumPerFuelType.values().stream().mapToDouble(Double::doubleValue).sum();

        Map<FuelType, Double> result = new EnumMap<>(FuelType.class);
        for (Map.Entry<FuelType, Double> entry : monthlyGenerationMixSumPerFuelType.entrySet()) {
            FuelType fuelType = entry.getKey();
            double sum = entry.getValue();
            double percentage = (sum / totalSumOfPercentages) * 100.0;
            result.put(fuelType, Math.round(percentage * 100.0) / 100.0);
        }

        return result;
    }

    private void addEntryToMonthlyGenerationMix(GenerationDataEntry generationDataEntry, Map<FuelType, Double> monthlyGenerationMix) {

        for (GenerationMix generationMix : generationDataEntry.getGenerationmix()) {
            FuelType fuelType = generationMix.getFuel();
            double perc = generationMix.getPerc();

            monthlyGenerationMix.put(fuelType, monthlyGenerationMix.get(fuelType) + perc);
        }
    }

    private static Map<FuelType, Double> getInitialGenerationMixMap() {
        Map<FuelType, Double> monthlyGenerationMix = new EnumMap<>(FuelType.class);

        for (FuelType fuelType : FuelType.values()) {
            monthlyGenerationMix.put(fuelType, 0.0);
        }

        return monthlyGenerationMix;
    }

    private OpenVoltDataResponse getOpenVoltData() {
        final String OPENVOLT_URL_JAN="https://api.openvolt.com/v1/interval-data?meter_id=6514167223e3d1424bf82742&granularity=hh&start_date=2023-01-01&end_date=2023-02-01";

        return getData(OPENVOLT_URL_JAN, OPENVOLT_KEY, OpenVoltDataResponse.class);
    }

    private CarbonIntensityDataResponse getCarbonIntensityData() {
        final String CARBON_DATA_URL_JAN = "https://api.carbonintensity.org.uk/intensity/2023-01-01T00:30Z/2023-01-31T24:00Z";

        return getData(CARBON_DATA_URL_JAN, null, CarbonIntensityDataResponse.class);
    }

    private GenerationDataResponse getGenerationMixData() {
        final String GENERATION_MIX_DATA_URL_JAN = "https://api.carbonintensity.org.uk/generation/2023-01-01T00:30Z/2023-01-31T24:00Z";

        return getData(GENERATION_MIX_DATA_URL_JAN, null, GenerationDataResponse.class);
    }

    public <T> T getData(String url, String apiKey, Class<T> responseClass) {
        String responseData = getApiResponse(url, apiKey);

        try {
            return objectMapper.readValue(responseData, responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getApiResponse(String requestUrl, String apiKey) {
        OkHttpClient client = new OkHttpClient();

        Request request = createApiRequest(requestUrl, apiKey);

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                logger.error("API request failed with status code: {}", response.code());
            }
        } catch (Exception e) {
            logger.error("Failed with exception ", e);
        }
        return null;
    }

    @NotNull
    private static Request createApiRequest(String requestUrl, String apiKey) {

        Request.Builder requestBuilder = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("accept", "application/json");

        if (Objects.nonNull(apiKey)) {
            requestBuilder.addHeader("x-api-key", apiKey);
        }

        return requestBuilder.build();
    }
}
