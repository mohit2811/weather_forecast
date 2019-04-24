package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class add_new_location extends AppCompatActivity {

    private EditText  country_et;
    private String loc_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.enter_city);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());

                loc_s = place.getAddress().toString();


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });


    }


    public void add_location_button(View v){

        if(loc_s.equals("")) {

            Toast.makeText(add_new_location.this , "please select your city" , Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);
        String email = sp.getString("user_id" , "");
        String name = sp.getString("user_name" , "");
        createaccount data = new createaccount(name, email,loc_s);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("user").child(email.replace(".","")).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {

            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Toast.makeText(add_new_location.this , "Location Added" , Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(add_new_location.this , "Error" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void arrow_add_location(View v){
        finish();
    }
}