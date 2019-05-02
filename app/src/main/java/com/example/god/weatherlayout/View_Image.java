package com.example.god.weatherlayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class View_Image extends AppCompatActivity {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__image);

        img = (ImageView) findViewById(R.id.big_image);

        Glide.with(this).load(getIntent().getStringExtra("image")).into(img);
    }
}
