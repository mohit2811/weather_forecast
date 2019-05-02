package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class weather_information extends AppCompatActivity {

    RecyclerView image_recycle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_information);

        image_recycle = (RecyclerView) findViewById(R.id.image_recycler);
        image_recycle.setLayoutManager(new LinearLayoutManager(weather_information.this , LinearLayoutManager.VERTICAL , false));

        get_images();
    }

    public void arrow_information(View v){
      finish();
    }


    /*public void get_images()*/
/*
    {




        JSONObject job = new JSONObject();

        try {

            job.put("city",getIntent().getStringExtra("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ip_address.ip+"/weather_forecast/get_image.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jarr = response.getJSONArray("result");
                    images_adapter ad = new images_adapter(jarr , weather_information.this);

                    image_recycle.setAdapter(ad);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)

            {
                System.out.println(error);
            }
        });

        app_controller.AppController app = new app_controller.AppController(weather_information.this);

        app.addToRequestQueue(jobreq);
    }
*/


    private void get_images()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        final String email = auth.getCurrentUser().getEmail().replace("." , "");


        DatabaseReference reference = database.getReference("images").child(email);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Images_data_model> list = new ArrayList<>();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    Images_data_model data_model = snapshot.getValue(Images_data_model.class);


                    list.add(data_model);

                }

                images_adapter ad = new images_adapter(list , weather_information.this);

                image_recycle.setAdapter(ad);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
