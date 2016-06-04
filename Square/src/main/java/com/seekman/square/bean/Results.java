package com.seekman.square.bean;

import java.util.ArrayList;

/**
 * Created by alice on 2016/6/2.
 */
public class Results {
    private String currentCity;
    private ArrayList<Index> index;
    private String pm25;
    private ArrayList<WeatherData> weather_data;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public ArrayList<Index> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Index> index) {
        this.index = index;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public ArrayList<WeatherData> getWeather_data() {
        return weather_data;
    }

    public void setWeather_data(ArrayList<WeatherData> weather_data) {
        this.weather_data = weather_data;
    }
}
