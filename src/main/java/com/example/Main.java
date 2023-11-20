package com.example;

public class Main {
    public static void main(String[] args) {
        // src/main/java/com/example/Test_Data.txt
        // Create an instance of ProbabilityGenerator and WeatherModel
        String FilePath = "C:/Users/robot/Labs/CRCP-Final-Project-Asare/src/main/java/com/example/Test_Data.txt";
        WeatherDataParser parser = new WeatherDataParser(FilePath);
        MarkovModel markov = new MarkovModel(parser.getWeatherDays());

        // Example: Predicting weather for a specific date
        markov.updateOrder("15/06/2023");
        WeatherDay predictedWeather = markov.predictTomorrow("15/06/2023");

        // Output the prediction
        System.out.println("Predicted Weather: " + predictedWeather);
    }
}
