package com.example.android.climapp;

import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class WeatherData {

    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

    static class Weather {
        String description;
        String icon;
    }

    static class Main {
        float temp;
    }

    List<Weather> weather;

    Main main;

    String name;

    // A method that converts temperature from Kelvin degrees to Celsius
    String getTemperatureInCelsius() {
        float temp = main.temp - 273.15f;
        return String.format("%.2f", temp);
    }

    // getIconAddress concatenates the base address and the specific code for
    // the icon
    public String getIconAddress() {
        return ICON_ADDR + weather.get(0).icon + ".png";
    }

    public String getDescription() {
        if (weather != null && weather.size() > 0)
            return weather.get(0).description;
        return null;
    }
}
