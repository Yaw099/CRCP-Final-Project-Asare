//Yaw Asare- 11/21/2023
package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // src/main/java/com/example/nyc_temperature.csv
        // C:/Users/robot/Labs/CRCP-Final-Project-Asare/src/main/java/com/example/nyc_temperature.csv
        // Create an instance of ProbabilityGenerator and WeatherModel
        String FilePath = "src/main/java/com/example/nyc_temperature-1.csv";

        // Initialize the WeatherDataParser with the CSV file
        WeatherDataParser parser = new WeatherDataParser(FilePath);

        // Initialize the MarkovModel with the parsed weather data
        MarkovModel markov = new MarkovModel(parser.getWeatherDays());
        

        // Calculate transition probabilities for the Markov Model
        markov.calcTransitionProbs();

        // Now you can use the Markov Model to make predictions
        // Example: Predict the weather for the next day given a specific date
        // String date = "15/06/2019"; // Example date in DD/MM/YYYY format
        // WeatherDay predictedWeather = markov.predictTomorrow(date);

        // Print the predicted weather
        // System.out.println("Predicted weather for " + date + ": " + predictedWeather);

        // Additional: Loop through the entire year and print predictions for each day
        // This can be done by iterating through the dates in the dataset
        List<WeatherDay> predictedWeatherDays = new ArrayList<>();
        for (WeatherDay day : parser.getWeatherDays()) {
            WeatherDay nextDayPrediction = markov.predictTomorrow(day.getDate());
            predictedWeatherDays.add(nextDayPrediction);
            System.out.println("Predicted weather for next day after " + day.getDate() + ": " + nextDayPrediction);
        }
        // Write the predicted data to a CSV file
        CSVOutput.writeDataToCSV(predictedWeatherDays, "src/main/java/com/example/output.csv");
    }
}
