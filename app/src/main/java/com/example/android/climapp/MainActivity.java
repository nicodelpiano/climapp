package com.example.android.climapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "62f72d3dcebf98e042b9fe65b733cac7";
    private static final String URL_ASK = "http://api.openweathermap.org/data/2.5/weather?q=";

    private String makeUrl (String city) {
        return URL_ASK + city + "&appid=" + APP_ID;
    }

    private String cityName;
    private Button cityButton;
    private EditText cityEditText;
    private JSONObject weatherInfo;

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

        // This allows us to use the resources of the activity
        Configuration configInfo = getResources().getConfiguration();

        // wtf stands for weather three fragment :P
        WeatherThreeFragment wtf = new WeatherThreeFragment();

        fragmentTransaction.add(R.id.activity_main, wtf);

        fragmentTransaction.commit();

    }

    public StringRequest makeStringRequest (String cityNameArg) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, makeUrl(cityNameArg),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        Log.v("Results", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.v("Error", error.toString());

            }
        });

        return stringRequest;
    }


    public JsonObjectRequest makeJSONRequest (String cityNameArg) {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, makeUrl(cityNameArg), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        weatherInfo = response;
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

}
