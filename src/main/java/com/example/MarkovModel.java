package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkovModel {
    private int currentOrder;
    private List<WeatherDay> weatherData;
    private Map<String, Map<String, Double>> transitionProbabilities;

    /**
     * Initializes the Markov Model with weather data.
     *
     * @param wd The historical weather data to base predictions on.
     */
    public MarkovModel(List<WeatherDay> wd) {
        this.weatherData = wd; // Assigning the provided weather data to the class variable.
        this.currentOrder = 1; // Initializing the order of the Markov chain.
    }

    /**
     * Updates the order of the Markov Model based on the current date.
     * The order increases as each day passes until December 31st.
     *
     * @param currentDate The current date in the format DD/MM/YYYY.
     */
    public void updateOrder(String currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Set the target date to December 31th of the year 2019
            Calendar targetDate = Calendar.getInstance();
            targetDate.set(Calendar.MONTH, Calendar.DECEMBER);
            targetDate.set(Calendar.DAY_OF_MONTH, 31);
            targetDate.set(Calendar.YEAR, 2019); //Year of the Dataset

            // Update the order based on the number of days until December 31st, 2019
            if (calendar.before(targetDate)) {
                int daysUntilTarget = daysBetween(calendar, targetDate);
                this.currentOrder = daysUntilTarget;
            } else {
                this.currentOrder = 1; // Reset to 1 after December 31st, 2019
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the number of days between two Calendar dates.
     *
     * @param start The start date.
     * @param end The end date.
     * @return The number of days between the start and end dates.
     */
    private int daysBetween(Calendar start, Calendar end) {
        long startInMillis = start.getTimeInMillis();
        long endInMillis = end.getTimeInMillis();
        long differenceInMillis = endInMillis - startInMillis;
        return (int) (differenceInMillis / (24 * 60 * 60 * 1000));
    }

    

    /**
     * Predicts the next day's weather based on the Markov Model.
     *
     * @param currentDate The current date for which the prediction is to be made.
     * @return A WeatherDay object representing the predicted weather.
     */
    public WeatherDay predictTomorrow(String currentDate) {
        // Find the index of the current date in the weatherData list
        int currentDateIndex = findIndexOfDate(currentDate);
        if (currentDateIndex == -1 || currentDateIndex < currentOrder - 1 || currentDateIndex >= weatherData.size() - 1) {
            // Handle cases where there's not enough data to form a complete state or if it's the last date
            return new WeatherDay();
        }

        // Create the current state from the sequence of the last currentOrder days
        String currentState = createCombinedState(currentDateIndex - currentOrder + 1, currentOrder);

         // Get the most probable next state
         Map<String, Double> possibleNextStates = transitionProbabilities.getOrDefault(currentState, new HashMap<>());
         String mostProbableNextState = selectMostProbableState(possibleNextStates);

        // // Get the current WeatherDay based on the currentDate
        // WeatherDay currentDay = weatherData.get(currnentDateIndex);

        return createWeatherDayFromState(mostProbableNextState, currentDateIndex);
        // return weatherData.get(currentDateIndex + 1);
    }

    private String selectMostProbableState(Map<String, Double> possibleNextStates) {
        String mostProbableState = null;
        double maxProbability = -1.0;
    
        for (Map.Entry<String, Double> entry : possibleNextStates.entrySet()) {
            if (entry.getValue() > maxProbability) {
                mostProbableState = entry.getKey();
                maxProbability = entry.getValue();
            }
        }
    
        return mostProbableState != null ? mostProbableState : "defaultState"; // Replace with an appropriate default state
    }

    private WeatherDay createWeatherDayFromState(String state, int currentDateIndex) {
        if (state == null || state.isEmpty() || currentDateIndex < 0 || currentDateIndex >= weatherData.size()) {
            return new WeatherDay();
        }
        
        String[] temps = state.split(",");
        float predictedTavg = Float.parseFloat(temps[temps.length - 1]); // Taking the last temperature in the sequence
    
        // Get the last day's weather data to keep the current values of date, tmax, and tmin
        WeatherDay lastDay = weatherData.get(currentDateIndex);

        // Create a new WeatherDay with the predicted temperature
        // Other parameters are set to default values or estimated values
        return new WeatherDay(lastDay.getDate(), lastDay.getMax(), lastDay.getMin(), predictedTavg);
    }

    /**
     * Finds the index of the given date in the weatherData list.
     *
     * @param date The date to find in the format DD/MM/YYYY.
     * @return The index of the date in the weatherData list, or -1 if not found.
     */
    private int findIndexOfDate(String date) {
        for (int i = 0; i < weatherData.size(); i++) {
            if (weatherData.get(i).getDate().equals(date)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calculates the transition probabilities between different weather states.
     */
    public void calcTransitionProbs() {
        transitionProbabilities = new HashMap<>();

        // Loop over the weather data
        for (int i = 0; i < weatherData.size() - currentOrder; i++) {
            // Create a state from a sequence of days
        String currentState = createCombinedState(i, currentOrder);
        String nextState = createCombinedState(i + 1, currentOrder);

        transitionProbabilities.putIfAbsent(currentState, new HashMap<>());
        Map<String, Double> stateTransitions = transitionProbabilities.get(currentState);
        stateTransitions.put(nextState, stateTransitions.getOrDefault(nextState, 0.0) + 1);

            // float currentState = stateRepresentation(weatherData.get(i));
            // float nextState = stateRepresentation(weatherData.get(i + 1));
    
            // transitionProbabilities.putIfAbsent(currentState, new HashMap<>());
            // Map<Float, Double> stateTransition = transitionProbabilities.get(currentState);
            // stateTransition.put(nextState, stateTransition.getOrDefault(nextState, 0.0) + 1);
        }
    

        // Normalize the probabilities
        for (Map.Entry<String, Map<String, Double>> entry : transitionProbabilities.entrySet()) {
        double totalTransitions = entry.getValue().values().stream().mapToDouble(Double::doubleValue).sum();
        Map<String, Double> normalizedTransitions = entry.getValue();
        
            for (String nextState : normalizedTransitions.keySet()) {
            normalizedTransitions.put(nextState, normalizedTransitions.get(nextState) / totalTransitions);
            }
        }
        // for (Map<Float, Double> transitions : transitionProbabilities.values()) {
        //     double totalTransitions = transitions.values().stream().mapToDouble(Double::doubleValue).sum();
        //     transitions.replaceAll((state, count) -> count / totalTransitions);
        // }
    }

    private String createCombinedState(int startIndex, int order) {
    StringBuilder stateBuilder = new StringBuilder();
    for (int i = startIndex; i < startIndex + order; i++) {
        stateBuilder.append(stateRepresentation(weatherData.get(i))).append(",");
    }
    return stateBuilder.toString();
    }

    /**
     * Generates a simplified state representation for a WeatherDay.
     * 
     * @param day The WeatherDay object.
     * @return An int representing the state.
     */
    private float stateRepresentation(WeatherDay day) {
        // Return the average temperature as the state
        return day.getAvg();
    }

    // /**
    //  * Initializes the transition probabilities of the Markov Model based on historical weather data.
    //  */
    // public void initializeTransitionProbabilities() {
    //     transitionProbabilities = new HashMap<>();

    //     // Example: Process weather data to calculate initial transition probabilities
    //     for (int i = 0; i < weatherData.size() - 1; i++) {
    //         String currentState =  weatherData.get(i).toString(); // Simplified representation of weather state
    //         String nextState = weatherData.get(i + 1).toString();

    //         transitionProbabilities.putIfAbsent(currentState, new HashMap<>());
    //         Map<String, Double> stateTransitions = transitionProbabilities.get(currentState);
    //         stateTransitions.put(nextState, stateTransitions.getOrDefault(nextState, 0.0) + 1);
    //     }

    //     // Normalize probabilities
    //     for (Map<String, Double> stateTransitions : transitionProbabilities.values()) {
    //         double total = stateTransitions.values().stream().mapToDouble(Double::doubleValue).sum();
    //         stateTransitions.replaceAll((key, value) -> value / total);
    //     }
    // }

    // /**
    //  * Updates the Markov Model with new weather data.
    //  *
    //  * @param newWeatherData The new weather data to be incorporated.
    //  */
    // public void updateModel(List<WeatherDay> newWeatherData) {
    //     // Placeholder for updating the model with new data
    //     // Logic to integrate new data into the existing model goes here
    // }

    // /**
    //  * Analyzes the accuracy of the Markov Model's predictions.
    //  *
    //  * @param actualWeatherData The actual weather data for comparison.
    //  * @return The accuracy metric of the model.
    //  */
    // public double analyzeModelAccuracy(List<WeatherDay> actualWeatherData) {
    //     // Placeholder for accuracy calculation
    //     double accuracy = 0.0;
    //     // Logic to compare predicted data with actual data and calculate accuracy
    //     return accuracy;
    // }

    // /**
    //  * Predicts the weather for a specific duration.
    //  *
    //  * @param startDate The start date for prediction.
    //  * @param numberOfDays The number of days to predict.
    //  * @return A list of predicted WeatherDay objects.
    //  */
    // public List<WeatherDay> predictWeatherForDuration(String startDate, int numberOfDays) {
    //     // Placeholder for multi-day weather prediction
    //     List<WeatherDay> predictions = new ArrayList<>();
    //     // Logic to predict weather for each day in the specified duration
    //     return predictions;
    // }

    // /**
    //  * Saves the current state of the Markov Model.
    //  *
    //  * @param saveFilePath The file path to save the model state.
    //  */
    // public void saveModelState(String saveFilePath) {
    //     // Placeholder for saving the model state
    //     // Logic to serialize and save the model state goes here
    // }

    // /**
    //  * Loads a saved state into the Markov Model.
    //  *
    //  * @param loadFilePath The file path from which to load the model state.
    //  */
    // public void loadModelState(String loadFilePath) {
    //     // Placeholder for loading the model state
    //     // Logic to deserialize and load the model state goes here
    // }


}
