//Yaw Asare- 11/21/2023
package com.example;

public class WeatherDay {
    private String date;
    private float tmax;
    private float tmin;
    private float tavg;


    public WeatherDay(String date, float tmax, float tmin, float tavg) {
        this.date = date;
        this.tmax = tmax;
        this.tmin = tmin;
        this.tavg = tavg;
    }
 

    public WeatherDay(){
        this.date = "End of year";
    }

    public String getDate(){return date;}
    public float getMax(){return tmax;}
    public float getMin(){return tmin;}    
    public float getAvg(){return tavg;}

    public String toCSVString() {
        // Format the WeatherDay data as a CSV line
        return String.format("%s,%f,%f,%f", this.date, this.tmax, this.tmin, this.tavg);
    }

    @Override
    public String toString() {
        return "WeatherDay{" +
        ", tmax = " + tmax +
        ", tmin = " + tmin +
        ", Predicted tavg = " + tavg + '}';
    }

}
