package com.example.android.climapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class WeatherData implements Parcelable {

    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

    static class City {
        String name;
    }

    static class Weather implements Parcelable {
        String description;
        String icon;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Bundle bundle = new Bundle();
            bundle.putString("description", description);
            bundle.putString("icon", icon);
            dest.writeBundle(bundle);
        }

        public static final Parcelable.Creator<Weather> CREATOR = new Creator<Weather>() {

            @Override
            public Weather createFromParcel(Parcel source) {
                // read the bundle containing key value pairs from the parcel
                Bundle bundle = source.readBundle();

                // instantiate the forecast info using values from the bundle
                return new Weather(bundle.getString("description"),
                        bundle.getString("icon"));
            }

            @Override
            public Weather[] newArray(int size) {
                return new Weather[size];
            }

        };

        private Weather (String description, String icon) {
            icon = icon;
            description = description;
        }

    }

    static class Temp implements Parcelable {
        float day;
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

        public static final Parcelable.Creator<Temp> CREATOR = new Creator<Temp>() {

            @Override
            public Temp createFromParcel(Parcel source) {
                // read the bundle containing key value pairs from the parcel
                Bundle bundle = source.readBundle();

                // instantiate the forecast info using values from the bundle
                return new Temp(bundle.getFloat("day"),
                        bundle.getFloat("min"), bundle.getFloat("max"));
            }

            @Override
            public Temp[] newArray(int size) {
                return new Temp[size];
            }

        };

        private Temp (Float tDay, Float tMin, Float tMax) {
            day = tDay;
            min = tMin;
            max = tMax;
        }
    }

    static class ForecastInfo implements Parcelable {
        //float humidity;
        //float pressure;
        Temp temp;
        ArrayList<Weather> weather;
        //float speed;
        //float deg;
        //int clouds;
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

        private ForecastInfo (Temp temp, ArrayList<Weather> weatherList) {
            this.temp = temp;
            this.weather = weatherList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Bundle bundle = new Bundle();

            bundle.putParcelable("temp", this.temp);
            bundle.putParcelableArrayList("weatherList", this.weather);

            dest.writeBundle(bundle);
        }

        public static final Parcelable.Creator<ForecastInfo> CREATOR = new Creator<ForecastInfo>() {

            @Override
            public ForecastInfo createFromParcel(Parcel source) {
                // read the bundle containing key value pairs from the parcel
                Bundle bundle = source.readBundle();

                // instantiate the forecast info using values from the bundle
                return new ForecastInfo((Temp) bundle.get("temp"),
                        (ArrayList<Weather>) bundle.get("weatherList"));
            }

            @Override
            public ForecastInfo[] newArray(int size) {
                return new ForecastInfo[size];
            }

        };
    }

    // Relevant data for WeatherData
    // The city (which only has a name in it)
    City city;
    // The array of forecast info extracted from the JSON
    ArrayList<ForecastInfo> list;

    private WeatherData (City city, ArrayList<ForecastInfo> forecastList) {
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
        bundle.putParcelable("forecastList", this.list.get(0));

        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<WeatherData> CREATOR = new Creator<WeatherData>() {

        @Override
        public WeatherData createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();
        //    ArrayList<ForecastInfo> forecastList = source.readArrayList(ForecastInfo.class.getClassLoader());
            // instantiate the weather using values from the bundle
            return new WeatherData(bundle.getString("cityName"),
                    (ForecastInfo) bundle.getParcelable("forecastList"));
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }

    };

    private WeatherData (String name, ForecastInfo flist) {
        City cityNew = new City();
        cityNew.name = name;
        city = cityNew;
        list = new ArrayList();
        list.add(flist);
    }

}