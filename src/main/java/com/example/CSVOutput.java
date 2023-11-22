//Yaw Asare- 11/21/2023
package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVOutput {
    public static void writeDataToCSV(List<WeatherDay> weatherDays, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            // Write the header line if necessary
            writer.append("Date,Tmax,Tmin,Tavg\n"); // Adjust with actual headers if needed

            // Write data
            for (WeatherDay day : weatherDays) {
                writer.append(day.toCSVString()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
