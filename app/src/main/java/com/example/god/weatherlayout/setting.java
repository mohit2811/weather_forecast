package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class setting extends AppCompatActivity {
    public static TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        temp = (TextView) findViewById(R.id.temp_txt);

        SharedPreferences sp = getSharedPreferences("settings" ,MODE_PRIVATE);
        if(!sp.getString("temp_unit" , "").equals(""))
        {
            temp.setText(sp.getString("temp_unit" , ""));
        }
    }

    public void arrow_setting(View v){
        finish();
    }

    public void temperature_unit(View v){
        pop_up pop = new pop_up(setting.this);

        pop.show();
    }


}
