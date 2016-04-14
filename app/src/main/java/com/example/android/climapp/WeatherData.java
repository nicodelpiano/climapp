package com.example.android.climapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class WeatherData implements Parcelable {

    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

    static class City {
        String name;
    }

    static class Weather {
        String description;
        String icon;
    }

    static class Temp implements Parcelable {
        float day;
        float night;
        float eve;
        float morning;
        float min;
        float max;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Bundle bundle = new Bundle();

            bundle.putFloat("day", day);
            bundle.putFloat("min", min);
            bundle.putFloat("max", max);

            dest.writeBundle(bundle);
        }
    }

    static class ForecastInfo implements Parcelable {
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

        ForecastInfo (Temp temp) {
            this.temp = temp;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Bundle bundle = new Bundle();

            bundle.putParcelable("temp", this.temp);

            dest.writeBundle(bundle);
        }

        public static final Parcelable.Creator<ForecastInfo> CREATOR = new Creator<ForecastInfo>() {

            @Override
            public ForecastInfo createFromParcel(Parcel source) {
                // read the bundle containing key value pairs from the parcel
                Bundle bundle = source.readBundle();

                // instantiate a person using values from the bundle
                return new ForecastInfo((Temp) bundle.get("temp"));
            }

            @Override
            public ForecastInfo[] newArray(int size) {
                return new ForecastInfo[size];
            }

        };
    }

    City city;
    ArrayList<ForecastInfo> list;

    WeatherData (City city, ArrayList<ForecastInfo> forecastList) {
        city = city;
        list = forecastList;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // create a bundle for the key value pairs
        Bundle bundle = new Bundle();

        // insert the key value pairs to the bundle
        bundle.putString("cityName", this.getName());
        bundle.putParcelableArrayList("forecastList", this.list);

        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<WeatherData> CREATOR = new Creator<WeatherData>() {

        @Override
        public WeatherData createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // instantiate a person using values from the bundle
            return new WeatherData((City) bundle.get("cityName"),
                    (ArrayList<ForecastInfo>) bundle.get("forecastList"));
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }

    };

}