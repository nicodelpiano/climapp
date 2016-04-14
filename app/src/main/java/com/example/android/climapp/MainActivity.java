package com.example.android.climapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
                Volley.newRequestQueue(getApplicationContext()).add(makeJSONRequest(cityName));
                /*Toast toast = Toast.makeText(getApplicationContext(), cityName, Toast.LENGTH_SHORT);
                toast.show();*/
            }
        });

        FragmentManager fragmentManager = getFragmentManager();

        // This begins the fragment transaction that let us play with the fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // This allows us to obtain the resources of the activity
        Configuration configInfo = getResources().getConfiguration();

        // wtf stands for weather three fragment :P
        WeatherFragment wtf = new WeatherFragment();

        fragmentTransaction.add(R.id.activity_main, wtf);
        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ListView list = savedInstanceState.getParcelable("list");

    }

    public JsonObjectRequest makeJSONRequest (String cityNameArg) {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, makeUrl(cityNameArg), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        weatherInfo = response;
                        weatherData = new Gson().fromJson(weatherInfo.toString(), WeatherData.class);
                        weatherListView = (ListView) findViewById(R.id.weather_three_fragment_list);

                        // get data from the table by the ListAdapter
                        ForecastInfoAdapter forecastInfoAdapter = new ForecastInfoAdapter(
                                getApplicationContext(),
                                R.layout.list_item_days, weatherData.list);

                        weatherListView.setAdapter(forecastInfoAdapter);

                        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        //intent.putParcelableArrayListExtra("weatherData", weatherData.list);
//        intent.putExtra("weatherData", weatherData);
        Bundle bundle = new Bundle();
        bundle.putParcelable("weatherDataBundle", weatherData);
        intent.putExtra("bundleObject", bundle);
        startActivity(intent);
    }
}