package com.example;

import java.util.*;

// import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class UnitTests {

    public static void main(String[] args) {
        // src/main/java/com/example/Test_Data.txt
        // Create an instance of ProbabilityGenerator and WeatherModel
        WeatherModel weatherModel = new WeatherModel();
        ProbabilityGenerator generator = new ProbabilityGenerator("C:/Users/robot/Labs/CRCP-Final-Project-Asare/src/main/java/com/example/Test_Data.txt\"", weatherModel);

        // Call and test methods of ProbabilityGenerator
        testReadAndPreprocessWeatherData(generator);
        testValidateData(generator);
        testCalculateTransitionProbabilities(generator);
        testWeatherModelEffects(weatherModel);
    }


    /*
     *  Tests for the Probability Generatot
     */

    private static void testReadAndPreprocessWeatherData(ProbabilityGenerator generator) {
        // C:/Users/robot/Labs/CRCP-Final-Project-Asare/src/main/java/com/example/
        // src/main/java/com/example/
        // C:/Users/David/Documents/GitHub/CRCP-Final-Project-Asare/src/main/java/com/example
        List<String> data = generator.readAndPreprocessWeatherData("src/main/java/com/example");
        System.out.println("Read and preprocessed Weather Data: " + data);
    }

    private static void testValidateData(ProbabilityGenerator generator) {
        boolean isValid = generator.validateData();
        System.out.println("Data Valid: " + isValid);
        // Additional checks can be added here
    }

    private static void testCalculateTransitionProbabilities(ProbabilityGenerator generator) {
        int orderM = 3; // Example order
        Map<List<String>, Map<String, Double>> probabilities = generator.calculateTransitionProbabilities(orderM);
        System.out.println("Calculated Transition Probabilities: " + probabilities);

        // Format and display the transition probabilities
        for (Map.Entry<List<String>, Map<String, Double>> entry : probabilities.entrySet()) {
            List<String> stateSequence = entry.getKey();
            Map<String, Double> transitions = entry.getValue();

            System.out.println("State Sequence: " + stateSequence);
            for (Map.Entry<String, Double> transition : transitions.entrySet()) {
                System.out.println(" - Transitions to " + transition.getKey() + " with probability " + transition.getValue());
            }
        }
        // Further validation of the probabilities can be done here
    }    

    private static void testWeatherModelEffects(WeatherModel weatherModel) {
        String currentWeather = "sunny"; // Example current weather
    String alteredWeather = weatherModel.simulateNaturalDisaster(currentWeather);
    System.out.println("Weather with Natural Disaster Effect: " + alteredWeather);

    // Test other scenarios, possibly looping through different weather states
    // and checking the output of simulateNaturalDisaster
    }


    
}