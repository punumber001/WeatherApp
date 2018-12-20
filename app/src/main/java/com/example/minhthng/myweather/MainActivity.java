package com.example.minhthng.myweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String WEATHER_REQUEST_URL =
            "https://api.openweathermap.org/data/2.5/weather?id=1581130&units=metric&appid=c7247b0125a7393fdaac84f1977f7909";

    private TextView location;
    private TextView weatherStatus;
    private TextView temp;
    private TextView sunrise;
    private TextView sunset;
    private TextView humidity;
    private TextView visibility;
    private TextView wind;
    private TextView pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //start asyntask to request current weather data
        WeatherAsynTask weatherAsynTask = new WeatherAsynTask();
        weatherAsynTask.execute(WEATHER_REQUEST_URL);

    }

    public void initViews()
    {
        location = findViewById(R.id.location);
        weatherStatus = findViewById(R.id.weather_status);
        temp = findViewById(R.id.temperature);
        sunrise = findViewById(R.id.sunrise_content);
        sunset = findViewById(R.id.sunset_content);
        humidity = findViewById(R.id.humidity_content);
        visibility = findViewById(R.id.visibility_content);
        wind = findViewById(R.id.wind_content);
        pressure = findViewById(R.id.pressure_content);
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
            location.setText(mWeather.getmLocation());
            weatherStatus.setText(mWeather.getmWeatherStatus());
            temp.setText(mWeather.getmTemperature() + "\u00B0C" );
            sunrise.setText(mWeather.getSunrise());
            sunset.setText(mWeather.getSunset());
            humidity.setText(mWeather.getHumidity() + " %");
            visibility.setText(mWeather.getVisibility());
            wind.setText(mWeather.getWind() + " km/h");
            pressure.setText(mWeather.getPressure() + " hPa");
        }
    }
}
