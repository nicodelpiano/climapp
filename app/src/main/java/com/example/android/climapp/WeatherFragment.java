package com.example.android.climapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by delpi on 02/04/16.
 */

public class WeatherFragment extends Fragment {

    ArrayAdapter<String> mForecastAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        return rootView;
    }
}
