package com.example.minhthng.myweather;

public class HourlyWeather {
    private long dateTime;
    private String weatherStatus;
    private String temp;

    public HourlyWeather(long dateTime, String weatherStatus, String temp) {
        this.dateTime = dateTime;
        this.weatherStatus = weatherStatus;
        this.temp = temp;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public java.lang.String getTemp() {
        return temp;
    }

}
