package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherForecast {

    private final int orderM;
    private final List<List<String>> stateSequences;
    private final List<Double> transitionProbabilities;

    public WeatherForecast(int orderM) {
        this.orderM = orderM;
        this.stateSequences = new ArrayList<>();
        this.transitionProbabilities = new ArrayList<>();
    }

    public void addTransition(List<String> sequence, double probability) {
        if (sequence.size() != orderM) {
            throw new IllegalArgumentException("Sequence length must be " + orderM);
        }
        stateSequences.add(sequence);
        transitionProbabilities.add(probability);
    }

    public String forecastNextState(List<String> currentState) {
        // Implement forecasting logic based on current state and transition probabilities
        // This can be a simple lookup or a more complex stochastic process
        return "Next State"; // placeholder
    }

    public static void main(String[] args) {
        WeatherForecast wf = new WeatherForecast(3); // Example for Markov chain of order 3

        // Example: Add transitions
        // wf.addTransition(Arrays.asList("sunny", "cloudy", "rainy"), 0.5);

        // Forecast next state
        // String nextState = wf.forecastNextState(Arrays.asList("sunny", "cloudy", "rainy"));
    }
}
