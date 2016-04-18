package com.example.android.climapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int DAYS = 4;
    private static final String APP_ID = "62f72d3dcebf98e042b9fe65b733cac7";
    //private static final String URL_ASK = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String URL_ASK = "http://api.openweathermap.org/data/2.5/forecast/daily?cnt="
            + DAYS + "&q=";
    //http://api.openweathermap.org/data/2.5/forecast/daily?q=Rosario&units=metric&cnt=3&appid=62f72d3dcebf98e042b9fe65b733cac7

    private String makeUrl (String city) {
        return URL_ASK + city + "&appid=" + APP_ID;
    }

    private String cityName;
    private Button cityButton;
    private EditText cityEditText;
    private JSONObject weatherInfo;
    private WeatherData weatherData;
    private ListView weatherListView;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cityButton = (Button) findViewById(R.id.button_ask);
        cityEditText = (EditText) findViewById(R.id.city_name);

        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = cityEditText.getText().toString();
                if (!cityName.isEmpty()) {
                    Volley.newRequestQueue(getApplicationContext()).add(makeJSONRequest(cityName));
                }
            }
        });

        FragmentManager fragmentManager = getFragmentManager();

        // This begins the fragment transaction that let us play with the fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // wf stands for weather fragment
        WeatherFragment wf = new WeatherFragment();

        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.activity_main, wf).commit();
        } else {
            weatherData = savedInstanceState.getParcelable("weatherData");
            fragmentTransaction.replace(R.id.activity_main, wf).commit();
        }
    }

    // Here, we save the data to restore later
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        savedInstanceState.putParcelable("weatherData", weatherData);
        savedInstanceState.putInt("position", position);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    // Here, we restore the state of the activity
    // Because we want to save the state of the view after, for example, a change of the
    // device orientation, we need to save the data and restore it as well
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        weatherData = (WeatherData) savedInstanceState.getParcelable("weatherData");
        position = savedInstanceState.getInt("position");

        if (weatherData != null) {

            ListView weatherListView = (ListView) findViewById(R.id.weather_fragment_list);

            // get data from the table by the ListAdapter
            ForecastInfoAdapter forecastInfoAdapter = new ForecastInfoAdapter(
                    getApplicationContext(),
                    R.layout.list_item_days, weatherData.list);
            weatherListView.setAdapter(forecastInfoAdapter);

            weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    position = pos;
                    showDetails(view);
                }
            });

            if(findViewById(R.id.bigscreen_layout) != null) {
                showDetails(findViewById(R.id.activity_main));
            }
        }
    }

    public JsonObjectRequest makeJSONRequest (String cityNameArg) {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, makeUrl(cityNameArg), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        weatherInfo = response;
                        weatherData = new Gson().fromJson(weatherInfo.toString(), WeatherData.class);
                        weatherListView = (ListView) findViewById(R.id.weather_fragment_list);

                        // get data from the table by the ListAdapter
                        ForecastInfoAdapter forecastInfoAdapter = new ForecastInfoAdapter(
                                getApplicationContext(),
                                R.layout.list_item_days, weatherData.list);

                        weatherListView.setAdapter(forecastInfoAdapter);

                        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                position = pos;
                                showDetails(view);
                            }
                        });

                        // Result handling
                        Log.v("Results", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.v("Error", error.toString());

            }
        });

        return jsonRequest;
    }

    public void showDetails(View view) {

        // Esto no es null cuando estamos en una pantalla grande, como una tablet
        if(findViewById(R.id.bigscreen_layout) != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DetailsFragment df = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("wData", weatherData);
            bundle.putInt("day", position);
            df.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_weather_layout, df).commit();

        } else {
            Intent intent = new Intent(this, ShowDetailsActivity.class);
            intent.putExtra("wData", weatherData);
            intent.putExtra("day", position);

            startActivity(intent);
        }
    }
}