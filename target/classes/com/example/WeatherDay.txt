package com.example;

public class WeatherDay {
    private String date;
    private float tmax;
    private float tmin;
    private float tavg;
    private float departure;
    private int HDD;
    private int CDD;
    private String precipitation;
    private String new_snow;
    private String snow_depth;

    public WeatherDay(String date, float tmax, float tmin, float tavg, float departure, int HDD, int CDD, String precipitation, String new_snow, String snow_depth) {
        this.date = date;
        this.tmax = tmax;
        this.tmin = tmin;
        this.tavg = tavg;
        this.departure = departure;
        this.HDD = HDD;
        this.CDD = CDD;
        this.precipitation = precipitation;
        this.new_snow = new_snow;
        this.snow_depth = snow_depth;
    }

    public String getDate(){return date;}
    public float getMax(){return tmax;}
    public float getMin(){return tmin;}    
    public float getAvg(){return tavg;}
    public float getDeparture(){return departure;}
    public int getHDD(){return HDD;}
    public int getCDD(){return CDD;}
    public String getPreciptation(){return precipitation;}
    public String getNewSnow(){return new_snow;}
    public String getSnowDepth(){return snow_depth;}




}
