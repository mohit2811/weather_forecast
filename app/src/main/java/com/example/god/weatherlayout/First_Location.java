package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GOD on 26-04-2017.
 */

public class First_Location extends Fragment {



    TextView city , temp , weather , img_gallery , remove_loc;

    ImageView camera_open  ;

    TextView sunrise_et;
    TextView wind_et;
    TextView temp_et;
    TextView sunset_et;
    TextView humidity_et;
    TextView pressure_et;
    TextView weather_et;
    TextView nineteen_et;
    TextView place_et;
    ImageView cloud_et;

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

        sunrise_et = v.findViewById(R.id.sunrise_et);
        wind_et = v.findViewById(R.id.wind_et);
        temp_et = v.findViewById(R.id.temp_et);
        sunset_et = v.findViewById(R.id.sunset_et);
        humidity_et = v.findViewById(R.id.humidity_et);
        pressure_et = v.findViewById(R.id.pressure_et);
        weather_et = v.findViewById(R.id.weather_et);
        nineteen_et = v.findViewById(R.id.nineteen_et);
        place_et= v.findViewById(R.id.city_txt);
        cloud_et = v.findViewById(R.id.cloud_et);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        get_weather();
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


    public void get_weather() {

        String city_name = getArguments().getString("city");

        //String city_name = "Amritsar";

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&APPID=8e4d35f79341370b8e448fa3f5d0433e";

        //String url = "http://api.openweathermap.org/data/2.5/weather?lat=31.6&lon=74.87&APPID=8e4d35f79341370b8e448fa3f5d0433e";



        JSONObject jsonObject = new JSONObject();
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray j_array =  response.getJSONArray("weather");
                    JSONObject j_object = j_array.getJSONObject(0);
                    String weather =  j_object.getString("main");

                    weather_et.setText(weather);

                    if(weather.equals("Cloudy"))
                    {
                        cloud_et.setImageDrawable(getResources().getDrawable(R.drawable.cloudyw));
                    }

                    if(weather.equals("Rain"))
                    {
                        cloud_et.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                    }

                    if(weather.equals("sunny"))
                    {
                        cloud_et.setImageDrawable(getResources().getDrawable(R.drawable.sunny));

                    }

                    JSONObject temp_json_object = response.getJSONObject("main");
                    Double temp = temp_json_object.getDouble("temp");
                    temp = temp - 275;

                    String s = String.format("%.2f", temp);
                    temp_et.setText(s+" C");
                    nineteen_et.setText(s);
                    int humidity =  temp_json_object.getInt("humidity");

                    humidity_et.setText(String.valueOf(humidity)+ " %");
                    int pressure =temp_json_object.getInt("pressure");

                    pressure_et.setText(String.valueOf(pressure)+" mb");
                    JSONObject wind_object =  response.getJSONObject("wind");
                    Double wind = wind_object.getDouble("speed");

                    wind_et.setText(String.valueOf(wind)+"km/h");

                    long sunrise =  response.getJSONObject("sys").getLong("sunrise");


                    Date date = new java.util.Date(sunrise*1000L);
                    // the format of your date
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm aa");
                    // give a timezone reference for formatting (see comment at the bottom)
                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                    sdf.setTimeZone(utc);



                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR, 5);
                    calendar.add(Calendar.MINUTE, 30);



                    String formattedDate = sdf.format(calendar.getTime());

                    sunrise_et.setText(formattedDate);

                    long sunset = response.getJSONObject("sys").getLong("sunset");

                    Date date2 = new java.util.Date(sunset*1000L);
                    // the format of your date
                    SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("hh:mm aa");
                    // give a timezone reference for formatting (see comment at the bottom)
                    TimeZone utc2 = TimeZone.getTimeZone("etc/UTC");
                    sdf.setTimeZone(utc2);
                    String formattedDate2 = sdf2.format(date2);

                    sunset_et.setText(formattedDate2);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,jsonObject,listener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);



    }


}
