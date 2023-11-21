package com.example;

import java.util.*;

public class MarkovModel {
    private int currentOrder;
    private List<WeatherDay> weatherData;
    private Map<String, Map<String, Double>> transitionProbabilities;

    /**
     * Initializes the Markov Model with weather data.
     *
     * @param weatherData The historical weather data to base predictions on.
     */
    public MarkovModel(List<WeatherDay> wd) {
        this.weatherData = weatherData;
        this.currentOrder = 1; // Starting order
    }

    /**
     * Updates the order of the Markov Model based on the current date.
     *
     * @param currentDate The current date in the format DD/MM/YYYY.
     */
    public void updateOrder(String currentDate) {

    }

    /**
     * Predicts the next day's weather based on the Markov Model.
     *
     * @param currentDate The current date for which the prediction is to be made.
     * @return A WeatherDay object representing the predicted weather.
     */
    public WeatherDay predictTomorrow(String currentDate) {
        // Placeholder
        return new WeatherDay("predictedDate", 0, 0, 0, 0.f, 0, 0, "predictedDeparture", 0.f, "predictedDepth");
    }

    /**
     * Initializes the transition probabilities of the Markov Model based on historical weather data.
     */
    public void initializeTransitionProbabilities() {
        transitionProbabilities = new HashMap<>();

        // Example: Process weather data to calculate initial transition probabilities
        for (int i = 0; i < weatherData.size() - 1; i++) {
            String currentState =  weatherData.get(i).toString(); // Simplified representation of weather state
            String nextState = weatherData.get(i + 1).toString();

            transitionProbabilities.putIfAbsent(currentState, new HashMap<>());
            Map<String, Double> stateTransitions = transitionProbabilities.get(currentState);
            stateTransitions.put(nextState, stateTransitions.getOrDefault(nextState, 0.0) + 1);
        }

        // Normalize probabilities
        for (Map<String, Double> stateTransitions : transitionProbabilities.values()) {
            double total = stateTransitions.values().stream().mapToDouble(Double::doubleValue).sum();
            stateTransitions.replaceAll((key, value) -> value / total);
        }
    }
}
