package com.example;

import java.util.*;
import java.io.*;

public class WeatherDataParser {
    
    private List<WeatherDay> weatherDays;

    /**
     * Parses the CSV file containing weather data and creates a list of WeatherDay objects.
     *
     * @param filePath The path to the CSV file.
     */
    public WeatherDataParser(String filePath) {
        this.weatherDays = readCsvFile(filePath);
    }

    private List<WeatherDay> readCsvFile(String filepath) {
    // Logic to read the CSV file and convert each line into a WeatherDay object
    List<WeatherDay> days = new ArrayList<>();
    return days; // Placeholder
    }

    public List<WeatherDay> getWeatherDays() {
        return weatherDays;
    }

    
}

