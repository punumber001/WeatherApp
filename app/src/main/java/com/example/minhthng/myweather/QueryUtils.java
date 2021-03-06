package com.example.minhthng.myweather;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String TAG = QueryUtils.class.getName();

    private QueryUtils(){}

    //Query data and return a list of Weather object
    public static Weather getWeatherData(String requestUrl){
        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e)
        {
            Log.e(TAG, "Error in making HTTP request.", e);
        }

        //init Weather objects from JSON response and create a list to store
        Weather currentWeather = getDataFromJson(jsonResponse);

        return currentWeather;
    }

    public static List<HourlyWeather> getHourlyWeatherList(String requestUrl)
    {
        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e)
        {
            Log.e(TAG, "Error in making HTTP request.", e);
        }

        //init Weather objects from JSON response and create a list to store
        List<HourlyWeather> hourlyWeatherList = getHourlyWeatherFromJson(jsonResponse);

        return hourlyWeatherList;
    }

    //Return new URL object from the given string URL
    private static URL createUrl(String stringURL)
    {
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (MalformedURLException e)
        {
            Log.e(TAG, "Problem building the URL", e);
        }
        return url;
    }

    //Make HTTP request to the given URL and return a JSON string
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e)
        {
            Log.e(TAG, "Error retrieving the weather JSON results." + e);
        }finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream != null)
            {
                // Closing the input stream could throw an IOException
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static Weather getDataFromJson(String weatherJSON)
    {
        if(TextUtils.isEmpty(weatherJSON))
        {
            return null;
        }

        try{
            JSONObject baseJsonResponse = new JSONObject(weatherJSON);

            //get current location
            String currentLocation = baseJsonResponse.getString("name");

            //get current weather status
            String currentWeatherStatus = baseJsonResponse.getJSONArray("weather").getJSONObject(0).getString("main");

            //get current temp
            String currentTemp = baseJsonResponse.getJSONObject("main").getString("temp");

            long sunrise = baseJsonResponse.getJSONObject("sys").getLong("sunrise") * 1000;
            Log.d(TAG, "Sunrise time: " + sunrise);
            long sunset = baseJsonResponse.getJSONObject("sys").getLong("sunset") * 1000;
            Log.d(TAG, "Sunset time: " + sunset);
            String humidity = baseJsonResponse.getJSONObject("main").getString("humidity");
            String wind = baseJsonResponse.getJSONObject("wind").getString("speed");
            String pressure = baseJsonResponse.getJSONObject("main").getString("pressure");
            String visibility = baseJsonResponse.getString("visibility");

            return new Weather(currentLocation, currentWeatherStatus, currentTemp, sunrise,
                    sunset, humidity, wind, pressure, visibility);

        }catch (JSONException e)
        {
            Log.e(TAG, "Error parsing weather JSON results", e);
        }

        return null;
    }

    private static List<HourlyWeather> getHourlyWeatherFromJson(String hourlyWeatherJSON)
    {
        if(TextUtils.isEmpty(hourlyWeatherJSON))
        {
            return null;
        }

        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(hourlyWeatherJSON);
            JSONArray hourlyWeatherArray = baseJsonResponse.getJSONArray("list");

            for(int i = 0; i < hourlyWeatherArray.length(); i++)
            {
                JSONObject hourlyWeather = hourlyWeatherArray.getJSONObject(i);

                long dateTime = hourlyWeather.getLong("dt") * 1000;
                String weatherId = hourlyWeather.getJSONArray("weather").getJSONObject(0).getString("id");
                String temp = hourlyWeather.getJSONObject("main").getString("temp");

                HourlyWeather mhourlyWeather = new HourlyWeather(dateTime, weatherId, temp);
                hourlyWeatherList.add(mhourlyWeather);
            }
        }catch (JSONException e)
        {
            Log.e(TAG, "Error parsing weather JSON results", e);
        }
        return hourlyWeatherList;
    }
}
