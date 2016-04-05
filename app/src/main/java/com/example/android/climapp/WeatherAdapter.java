package com.example.android.climapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by delpi on 03/04/16.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {

    public WeatherAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public WeatherAdapter(Context context, int resource, List<WeatherData> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_days, null);
        }

        WeatherData p = getItem(position);

        if (p != null) {
            TextView tt0 = (TextView) v.findViewById(R.id.list_item_day);
            ImageView tt1 = (ImageView) v.findViewById(R.id.list_item_image);
            TextView tt2 = (TextView) v.findViewById(R.id.list_item_info);
            TextView tt3 = (TextView) v.findViewById(R.id.list_item_temperature);

            // Harcoding the name of the days according to the position
            // (ask benja and mariano if this is OK)
            switch (position) {
                case 0 : tt0.setText("Hoy"); break;
                case 1 : tt0.setText("Manana"); break;
                case 2 : tt0.setText("Pasado Manana"); break;
            }
            Picasso.with(getContext()).load(p.getIconAddress(position)).into(tt1);
            tt2.setText(p.getDescription(position));
            tt3.setText(p.getTemperatureInCelsius(position));
        }

        return v;
    }
}
