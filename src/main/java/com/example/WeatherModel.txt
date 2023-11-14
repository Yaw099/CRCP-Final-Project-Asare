package com.example;
import java.util.Random;

public class WeatherModel {
    
    private static final Random random = new Random();

    /**
     * Checks if a natural disaster occurs.
     * 
     * @return true if a natural disaster, false otherwise.
     */
    private static boolean checkForNaturalDisaster() {
        int chance = 7; // 1 in 7 chance for a natural disaster
        return random.nextInt(chance) == 0; // Randomly returns true with a probability of 1/7
    }

    /**
     * Simulates the effect of a natural disaster on the weather.
     * 
     * @param currentWeather The current weather state.
     * @return The altered weather state due to the natural disaster.
     */
    public static String simulteNaturalDisaster(String currentWeather) {
        if (checkForNaturalDisaster()) {
            // Logic to modify the weather based on a natural disaster
            switch (currentWeather) {
                case "sunny":
                    return "heat wave"; // A heat wave occurs
                case "cloudy":
                    return "stormy"; // Rain intensifies
                case "rainy":
                    return "flood"; // Flooding conditions
                case "pcloudy":
                    return "tornado"; // A tornado occurs
                case "stormy":
                    return "hurricane"; // Storm intensifies
                case "windy":
                    return "tornado"; // A tornado occurs
                case "snowy": 
                    return "blizzard"; // A blizzard occurs
                case "hail":
                    return "blizzard"; // A blizzard occurs
                default:
            }
        }
        return currentWeather; // Default weather conditions
    }
}
