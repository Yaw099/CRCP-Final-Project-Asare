package com.example;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class ProbabilityGenerator {

    public List<String> readWeatherData(String filePath) {
        List<String> weatherData = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                if (!line.startsWith("Week")) {  // Skip week titles
                    String[] states = line.split(",");
                    Collections.addAll(weatherData, states);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    // Other methods...
}
