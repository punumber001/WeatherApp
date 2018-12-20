package com.example.minhthng.myweather;

public class Weather {

    //current location
    private String mLocation;

    //current status of the weather
    private String mWeatherStatus;

    //current temperature
    private String mTemperature;

    private String sunrise;
    private String sunset;
    private String humidity;
    private String wind;
    private String pressure;
    private String visibility;

    public Weather(String mLocation, String mWeatherStatus, String mTemperature, String sunrise,
                   String sunset, String humidity, String wind, String pressure, String visibility) {
        this.mLocation = mLocation;
        this.mWeatherStatus = mWeatherStatus;
        this.mTemperature = mTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.humidity = humidity;
        this.wind = wind;
        this.pressure = pressure;
        this.visibility = visibility;
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

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public String getPressure() {
        return pressure;
    }

    public String getVisibility() {
        return visibility;
    }
}
