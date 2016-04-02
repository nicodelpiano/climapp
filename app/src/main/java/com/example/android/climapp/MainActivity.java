package com.example.android.climapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button cityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cityButton = (Button) findViewById(R.id.button_ask);

        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FragmentManager fragmentManager = getFragmentManager();

        // This begins the fragment transaction that let us play with the fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // This allows us to use the resources of the activity
        Configuration configInfo = getResources().getConfiguration();

        // wtf stands for weather three fragment :P
        WeatherThreeFragment wtf = new WeatherThreeFragment();

        fragmentTransaction.add(R.id.activity_main, wtf);

        fragmentTransaction.commit();

    }

}
