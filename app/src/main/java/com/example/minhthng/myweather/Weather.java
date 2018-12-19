package com.example.minhthng.myweather;

public class Weather {

    //current location
    private String mLocation;

    //current status of the weather
    private String mWeatherStatus;

    //current temperature
    private String mTemperature;

    public Weather(String mLocation, String mWeatherStatus, String mTemperature) {
        this.mLocation = mLocation;
        this.mWeatherStatus = mWeatherStatus;
        this.mTemperature = mTemperature;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmWeatherStatus() {
        return mWeatherStatus;
    }

    public String getmTemperature() {
        return mTemperature;
    }
}
