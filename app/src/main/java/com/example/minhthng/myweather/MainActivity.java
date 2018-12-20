package com.example.minhthng.myweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String WEATHER_REQUEST_URL =
            "https://api.openweathermap.org/data/2.5/weather?id=1581130&units=metric&appid=c7247b0125a7393fdaac84f1977f7909";
    private static final String HOURLYWEATHER_REQUEST_URL =
            "https://api.openweathermap.org/data/2.5/forecast?id=1581130&units=metric&appid=c7247b0125a7393fdaac84f1977f7909";


    private TextView location;
    private TextView weatherStatus;
    private TextView temp;
    private TextView sunrise;
    private TextView sunset;
    private TextView humidity;
    private TextView visibility;
    private TextView wind;
    private TextView pressure;

    private HourlyWeatherAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //start asyntask to request current weather data


        ListView hourlyWeatherListView = findViewById(R.id.hourly_weather);

        mAdapter = new HourlyWeatherAdapter(this, new ArrayList<HourlyWeather>());

        hourlyWeatherListView.setAdapter(mAdapter);

        WeatherAsynTask weatherAsynTask = new WeatherAsynTask();
        weatherAsynTask.execute(WEATHER_REQUEST_URL);

        HourlyWeatherAsynTask hourlyWeatherAsynTask = new HourlyWeatherAsynTask();
        hourlyWeatherAsynTask.execute(HOURLYWEATHER_REQUEST_URL);
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

            Date sunriseDate = new Date(mWeather.getSunrise());
            sunrise.setText(formatTime(sunriseDate));

            Date sunsetDate = new Date(mWeather.getSunset());
            sunset.setText(formatTime(sunsetDate));

            humidity.setText(mWeather.getHumidity() + " %");
            visibility.setText(mWeather.getVisibility());
            wind.setText(mWeather.getWind() + " km/h");
            pressure.setText(mWeather.getPressure() + " hPa");
        }

        //format long milliseconds format to date format, ex: 4:55 AM
        private String formatTime(Date dateObject)
        {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            timeFormat.setTimeZone(Calendar.getInstance().getTimeZone());
            return timeFormat.format(dateObject);
        }

    }

    private class HourlyWeatherAsynTask extends AsyncTask<String, Void, List<HourlyWeather>>
    {
        @Override
        protected List<HourlyWeather> doInBackground(String... urls) {
            if(urls.length < 1 || urls[0] == null)
            {
                Log.d(TAG, "URL is null ");
                return null;
            }

            List<HourlyWeather> hourlyWeatherList = QueryUtils.getHourlyWeatherList(urls[0]);
            return hourlyWeatherList;
        }

        @Override
        protected void onPostExecute(List<HourlyWeather> hourlyWeatherList) {
            mAdapter.clear();
            Log.d(TAG, "The list size: " + hourlyWeatherList.size());
            if(hourlyWeatherList!= null && !hourlyWeatherList.isEmpty())
            {
                mAdapter.addAll(hourlyWeatherList);
            }
        }
    }
}
