package com.example.god.weatherlayout;

import android.app.ProgressDialog;
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

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_information);

        image_recycle = (RecyclerView) findViewById(R.id.image_recycler);
        image_recycle.setLayoutManager(new LinearLayoutManager(weather_information.this , LinearLayoutManager.VERTICAL , false));

        pd = new ProgressDialog(weather_information.this);

        pd.setTitle("Loading");
        pd.setMessage("Please wait ...");

        get_images();
    }

    public void arrow_information(View v){
      finish();
    }




    private void get_images()
    {
        pd.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        final String email = auth.getCurrentUser().getEmail().replace("." , "");


        DatabaseReference reference = database.getReference("images").child(email);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Images_data_model> list = new ArrayList<>();

                pd.hide();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    Images_data_model data_model = snapshot.getValue(Images_data_model.class);

                    data_model.image_id = snapshot.getKey();


                    if(data_model.location.equalsIgnoreCase(getIntent().getStringExtra("city"))) {


                        list.add(data_model);

                    }

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
