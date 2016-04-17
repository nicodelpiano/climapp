package com.example.android.climapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class ForecastInfoAdapter extends ArrayAdapter<WeatherData.ForecastInfo> {

    public ForecastInfoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ForecastInfoAdapter(Context context, int resource, List<WeatherData.ForecastInfo> objects) {
        super(context, resource, objects);
    }

    /*
        addOneDay:
        this function simply adds one day to the calendar
        and returns the format provided by dateFormat
     */
    static public String addOneDay(Calendar calendar, DateFormat dateFormat) {
        calendar.add(Calendar.DATE, 1);
        return dateFormat.format(calendar.getTime());
    }

    static public String getDay(int position) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM");
        String day = "";

        Calendar calendar = Calendar.getInstance();
        String dia3 = addOneDay(calendar, dateFormat);
        String dia4 = addOneDay(calendar, dateFormat);

        // Harcoding the name of the days according to the position
        // (ask benja and mariano if this is OK)
        switch (position) {
            case 0 : day = "Hoy"; break;
            case 1 : day = "Mañana"; break;
            case 2 : day = dia3; break;
            case 3 : day = dia4; break;
            default: day = "";
        }

        return day;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_days, null);
        }

        WeatherData.ForecastInfo forecastInfo = getItem(position);

        if (forecastInfo != null) {

            TextView tt0 = (TextView) v.findViewById(R.id.list_item_day);
            ImageView tt1 = (ImageView) v.findViewById(R.id.list_item_image);
            TextView tt2 = (TextView) v.findViewById(R.id.list_item_info);
            TextView tt3 = (TextView) v.findViewById(R.id.list_item_temperature);

            tt0.setText(getDay(position));
            Picasso.with(getContext()).load(forecastInfo.getIconAddress()).into(tt1);
            tt2.setText(forecastInfo.getDescription());
            tt3.setText(forecastInfo.getTemperatureInCelsius());
        }

        return v;
    }
}
