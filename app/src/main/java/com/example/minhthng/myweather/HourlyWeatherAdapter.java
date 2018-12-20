package com.example.minhthng.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HourlyWeatherAdapter extends ArrayAdapter<HourlyWeather> {

    public HourlyWeatherAdapter(Context context, List<HourlyWeather> hourlyWeatherList) {
        super(context, 0, hourlyWeatherList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.hourly_weather_item, parent, false);
        }

        //find the HourlyWeather object at the position
        HourlyWeather hWeather = getItem(position);

        TextView time = listItemView.findViewById(R.id.time);
        time.setText(formatTime(new Date(hWeather.getDateTime())));

        TextView weatherStatus = listItemView.findViewById(R.id.weather_status);
        weatherStatus.setText(hWeather.getWeatherStatus());

        TextView temp = listItemView.findViewById(R.id.temp);
        temp.setText(hWeather.getTemp());

        return listItemView;
    }

    //format long milliseconds format to date format, ex: 4 AM
    private String formatTime(Date dateObject)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h a");
        timeFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        return timeFormat.format(dateObject);
    }
}
