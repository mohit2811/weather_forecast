package com.example.god.weatherlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Thread th = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                }

                finally {

                    Intent i= new Intent(welcome_page.this,final_home_page.class);

                    startActivity(i);
                }

            }
        };
        th.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
