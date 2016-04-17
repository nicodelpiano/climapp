package com.example.android.climapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by delpi on 02/04/16.
 */
public class DetailsFragment extends Fragment {

    private int dayPos;
    private WeatherData weatherData;
    private TextView cityName;
    private TextView day;
    private ImageView image;
    private TextView info;
    private TextView humidity;
    private TextView pressure;
    private TextView temperature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();
        dayPos = intent.getIntExtra("day", 0);
        weatherData = intent.getParcelableExtra("wData");

        temperature = (TextView) rootView.findViewById(R.id.details_temperature);
        day = (TextView) rootView.findViewById(R.id.details_day);
        info = (TextView) rootView.findViewById(R.id.details_info);
        humidity = (TextView) rootView.findViewById(R.id.details_humidity);
        pressure = (TextView) rootView.findViewById(R.id.details_pressure);
        cityName = (TextView) rootView.findViewById(R.id.details_city);
        image = (ImageView) rootView.findViewById(R.id.details_image);

        temperature.setText(weatherData.getTemperatureInCelsius(dayPos));
        day.setText(ForecastInfoAdapter.getDay(dayPos));
        info.setText(weatherData.getDescription(dayPos));
        cityName.setText(weatherData.getName());
        pressure.setText(pressure.getText() + Float.toString(weatherData.getPressure(dayPos)));
        humidity.setText(humidity.getText() + Float.toString(weatherData.getHumidity(dayPos)));
        Picasso.with(getActivity().getApplicationContext()).load(weatherData.getIconAddress(dayPos)).into(image);

        return rootView;
    }
}
