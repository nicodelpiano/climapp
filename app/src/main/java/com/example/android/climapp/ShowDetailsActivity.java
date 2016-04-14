package com.example.android.climapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundleObject");
        intent.setExtrasClassLoader(WeatherData.class.getClassLoader());
        bundle.setClassLoader(WeatherData.class.getClassLoader());
        WeatherData weatherData = (WeatherData) bundle.getParcelable("weatherData");
        System.out.println(weatherData.city.name);

    }

}