package com.example.minhthng.myweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String WEATHER_REQUEST_URL =
            "https://api.openweathermap.org/data/2.5/weather?id=1581130&units=metric&appid=c7247b0125a7393fdaac84f1977f7909";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start asyntask to request current weather data
        WeatherAsynTask weatherAsynTask = new WeatherAsynTask();
        weatherAsynTask.execute(WEATHER_REQUEST_URL);

    }

    private class WeatherAsynTask extends AsyncTask<String, Void, Weather>
    {
        @Override
        protected Weather doInBackground(String... urls) {
            if(urls.length < 1 || urls[0] == null)
            {
                return null;
            }

            Weather currentWeather = QueryUtils.getWeatherData(urls[0]);
            return currentWeather;
        }


        @Override
        protected void onPostExecute(Weather mWeather) {
            //extract weather fields from the doInBackground return value
            String location = mWeather.getmLocation();
            String currentWeather = mWeather.getmWeatherStatus();
            String temp = mWeather.getmTemperature();

            //set data to the views
            TextView mLocationTv = findViewById(R.id.location);
            mLocationTv.setText(location);

            TextView mWeatherStatus = findViewById(R.id.weather_status);
            mWeatherStatus.setText(currentWeather);

            TextView mTemp = findViewById(R.id.temperature);
            mTemp.setText(temp);
        }
    }
}
