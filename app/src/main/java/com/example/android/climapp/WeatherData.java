package com.example.android.climapp;

import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class WeatherData {

    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

    static class City {
        String name;
    }

    static class Weather {
        String description;
        String icon;
    }

    static class Temp {
        float day;
        float night;
        float eve;
        float morning;
        float min;
        float max;
    }

    static class ForecastInfo {
        float humidity;
        float pressure;
        Temp temp;
        List<Weather> weather;
        float speed;
        float deg;
        int clouds;
        //float rain;

        String getTemperatureInCelsius() {
            float t = temp.day - 273.15f;
            return String.format("%.0f" + (char) 0x00B0, t);
        }

        public String getIconAddress() {
            return ICON_ADDR + weather.get(0).icon + ".png";
        }

        public String getDescription() {
            if (weather != null && weather.size() > 0)
                return weather.get(0).description;
            return null;
        }

    }

    City city;
    List<ForecastInfo> list;

    // A method that converts temperature from Kelvin degrees to Celsius
    String getTemperatureInCelsius(int day) {
        return list.get(day).getTemperatureInCelsius();
    }

    // getIconAddress concatenates the base address and the specific code for
    // the icon
    public String getIconAddress(int day) {
        return list.get(day).getIconAddress();
    }

    public String getName() {
        return city.name;
    }

    public String getDescription(int day) {
        return list.get(day).getDescription();
    }
}