package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GOD on 26-04-2017.
 */

public class First_Location extends Fragment {



    TextView city , temp , weather , img_gallery , remove_loc;

    ImageView camera_open  ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.first_location_layout , container , false);


        remove_loc = (TextView) v.findViewById(R.id.remove_loc);
        temp = (TextView) v.findViewById(R.id.temp_txt);
        weather = (TextView) v.findViewById(R.id.weather_txt);

        img_gallery = (TextView) v.findViewById(R.id.img_gallery);

        remove_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final_home_page.remove_loc(getArguments().getString("loc_id"),getActivity());
            }
        });

        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),weather_information.class);
                i.putExtra("city", getArguments().getString("city"));
                startActivity(i);
            }
        });

        camera_open = (ImageView) v.findViewById(R.id.camera_open);


        camera_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() ,CameraActivity.class);

                i.putExtra("city", getArguments().getString("city"));

                startActivity(i);
            }
        });
        city = (TextView) v.findViewById(R.id.city_txt);

        city.setText(getArguments().getString("city"));

        List<String> cityList = Arrays.asList(getArguments().getString("city").split(","));
//        get_weather(cityList.get(0));
        return v;
    }

   /* public void get_weather(String city)
    {
        JsonObjectRequest job = new JsonObjectRequest("http://api.openweathermap.org/data/2.5/forecast?q="+city+",IN&appid=858e7cdcac38b30691fa6a397ed40d91", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                  JSONObject job =  response.getJSONArray("list").getJSONObject(0);

                    int temp_m = ((int)(Float.parseFloat(job.getJSONObject("main").getString("temp"))));

                    SharedPreferences sp = getActivity().getSharedPreferences("settings" ,MODE_PRIVATE);

                    if(!sp.getString("temp_unit" , "").equals("")) {
                        if (sp.getString("temp_unit", "").equals("C")) {

                            int temp_f = (int) (temp_m - 273.15);
                            temp.setText(String.valueOf(temp_f));

                        } else {
                            int temp_f = (int) ((temp_m * 1.8) - 459.67);
                            temp.setText(String.valueOf(temp_f));
                        }
                    }
                    else {
                        int temp_f = (int) (temp_m - 273.15);
                        temp.setText(String.valueOf(temp_f));
                    }




                    weather.setText(job.getJSONArray("weather").getJSONObject(0).getString("main"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        job.setRetryPolicy(new DefaultRetryPolicy(20000 , 2, 2));

        app_controller.AppController app = new app_controller.AppController(getActivity());
        app.addToRequestQueue(job);

    }*/
}
