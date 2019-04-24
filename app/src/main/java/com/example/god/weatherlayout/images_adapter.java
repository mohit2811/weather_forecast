package com.example.god.weatherlayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by GOD on 16-05-2017.
 */

public class images_adapter extends RecyclerView.Adapter<images_view_holder> {

    JSONArray jarr;
    Activity a ;

    public  images_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr;
        this.a = a;
    }

    @Override
    public images_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new images_view_holder(LayoutInflater.from(a).inflate(R.layout.images_cell , parent , false));
    }

    @Override
    public void onBindViewHolder(images_view_holder holder, int position) {

        try {
            final JSONObject job = jarr.getJSONObject(position);


          holder.img.setImageBitmap( StringToBitMap( job.getString("Weather_Image")));

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        StringToBitMap( job.getString("Weather_Image")).compress(Bitmap.CompressFormat.PNG, 100, stream);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    byte[] byteArray = stream.toByteArray();

                    Intent in1 = new Intent(a, View_Image.class);
                    in1.putExtra("image",byteArray);

                    a.startActivity(in1);


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }

    // function to convert string image into bitmap image
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
