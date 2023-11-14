package com.example;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class ProbabilityGenerator {

    List<String> weatherData = new ArrayList<>();
    private WeatherModel weatherModel;
   
    public ProbabilityGenerator(WeatherModel weatherModel) {
        this.weatherData = new ArrayList<>();
        this.weatherModel = weatherModel;
    }

    /**
    * Reads weather data from a file.
    *
    * @param filePath The path to the text file containing the weather data.
    * @return A list of strings, each representing a weather state.
    */
    public List<String> readWeatherData(String filePath) {

        try {
            // Read all lines from the file at the given filePath
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                // Skip lines that start with "Week", as they are week titles
                if (!line.startsWith("Week")) {
                    // Split the line by commas to separate individual weather states
                    String[] states = line.split(",");

                    // Add all the states from the current line to the weatherData list
                    Collections.addAll(weatherData, states);
                }
            }
        } catch (IOException e) {
            // Print an error message if there's an issue reading the file
            e.printStackTrace();
        }
        // Return the list of weather states
        return weatherData;
    }

    /**
     * Calculates the transition probabilities for a Markov chain of order M.
     *
     * @param orderM The order of the Markov chain.
     * @return A map where each key is a sequence of M states, and the value is another map.
     *         The nested map's key is the next state, and the value is the probability of
     *         transitioning to that state.
     */
    // Other methods for calculating probabilities, etc., can be added here
    public Map<List<String>, Map<String, Double>> calculateTransitionProbabilities(int orderM) {
        // Map to store the count of each transition
        Map<List<String>, Map<String, Integer>> transitionCounts = new HashMap<>();
        // Map to store the calculated probabilities
        Map<List<String>, Map<String, Double>> transitionProbabilities = new HashMap<>();

        // Iterate through the weather data
        for (int i = 0; i <= weatherData.size() - orderM - 1; i++) {
            // Extract the current state sequence of length orderM
            List<String> currentState = new ArrayList<>(weatherData.subList(i, i + orderM));
            // Get the next state following the current state sequence
            String nextState = weatherData.get(i + orderM);

            // If the current state sequence isn't in the map, add it with a new HashMap
            if (!transitionCounts.containsKey(currentState)) {
                transitionCounts.put(currentState, new HashMap<String, Integer>());
            }
            // Get the transition map for the current state sequence
            Map<String, Integer> stateTransitions = transitionCounts.get(currentState);
            // Update the count for the transition to the next state
            int currentCount = stateTransitions.containsKey(nextState) ? stateTransitions.get(nextState) : 0;
            stateTransitions.put(nextState, currentCount + 1);
        }

        // Calculate probabilities based on the counts
        for (Map.Entry<List<String>, Map<String, Integer>> entry : transitionCounts.entrySet()) {
            // Map to store probabilities for the current state sequence
            Map<String, Double> stateProbabilities = new HashMap<>();
            // Calculate the total transitions from the current state sequence
            int totalTransitions = 0;
            for (int count : entry.getValue().values()) {
                totalTransitions += count;
            }

            // Calculate the probability for each transition
            for (Map.Entry<String, Integer> stateEntry : entry.getValue().entrySet()) {
                double probability = stateEntry.getValue() / (double) totalTransitions;
                stateProbabilities.put(stateEntry.getKey(), probability);
            }

            // Put the calculated probabilities in the final map
            transitionProbabilities.put(entry.getKey(), stateProbabilities);
        }

        return transitionProbabilities;
    }
    /**
     * Validates the weather data to ensure it's suitable for probability calculations.
     *
     * @return boolean - true if the data is valid, false otherwise.
     */
    public boolean validateData() {
        // Validate data
        // Check if weatherData is not null and not empty
        if (weatherData == null || weatherData.isEmpty()) {
            System.out.println("Weather data is null or empty.");
            return false;
        }

        // Check for null or empty strings within the weather data
        for (String state : weatherData) {
            if (state == null || state.trim().isEmpty()) {
                System.out.println("Invalid state found in weather data: " + state);
                return false;
            }
        }

        // Check if the weather data is long enough for the Markov chain order
        // For example, for a Markov chain of order 2, you need at least 3 data points
        int orderM = 2; // This should be set according to your model's order
        if (weatherData.size() < orderM + 1) {
            System.out.println("Insufficient data for the Markov chain of order " + orderM);
            return false;
        }

        // Add any additional validations as needed based on your specific requirements

        return true;

    }
    
    // Other helper methods as required
    

}
