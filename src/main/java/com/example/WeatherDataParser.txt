package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.text.SimpleDateFormat;


public class WeatherDataParser {
    
    private List<WeatherDay> weatherDays;

    /**
     * Constructor to initialize the WeatherDataParser.
     * Reads the CSV file and stores the parsed data.
     *
     * @param filePath The path to the CSV file.
     */
    public WeatherDataParser(String filePath) {
        this.weatherDays = readCsvFile(filePath);
    }

    /**
     * Gets the list of WeatherDay objects.
     *
     * @return A list of WeatherDay objects.
     */
    public List<WeatherDay> getWeatherDays() {
        return weatherDays;
    }

    /**
     * Reads the CSV file, parses each line, and creates WeatherDay objects.
     *
     * @param filePath The path to the CSV file.
     * @return A list of WeatherDay objects.
     */
    private List<WeatherDay> readCsvFile(String filepath) {
        // Logic to read the CSV file and convert each line into a WeatherDay object
        List<WeatherDay> days = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // Read the header line (and ignore it)
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split the line into values
                WeatherDay day = parseToWeatherDay(values);
                days.add(day);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * Parses a line from the CSV file and creates a WeatherDay object.
     *
     * @param values An array of string values representing a line from the CSV.
     * @return A WeatherDay object.
     */
    private WeatherDay parseToWeatherDay(String[] values) {
        String date = values[0];
        float tmax = Float.parseFloat(values[1].replace("\"", ""));
        float tmin = Float.parseFloat(values[2].replace("\"", ""));
        float tavg = Float.parseFloat(values[3].replace("\"", ""));

        return new WeatherDay(date, tmax, tmin, tavg);
    }

    /**
     * Calculates the monthly average temperatures.
     *
     * @return A map with months as keys and average temperatures as values.
     */
    public Map<String, Double> calcMonthlyAvgs() {
        Map<String, List<Float>> monthlyTemps = new HashMap<>();
        Map<String, Double> monthlyAvgs = new HashMap<>();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");

        for (WeatherDay day : weatherDays) {
            String month = monthFormat.format(day.getDate());
            monthlyTemps.putIfAbsent(month, new ArrayList<>());
            monthlyTemps.get(month).add(day.getAvg());
        }
        for (Map.Entry<String, List<Float>> entry : monthlyTemps.entrySet()) {
            double average = entry.getValue().stream().mapToDouble(Float::doubleValue).average().orElse(0);
            monthlyAvgs.put(entry.getKey(), average);
        }
        return monthlyAvgs;
    }
}

