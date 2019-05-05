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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by GOD on 16-05-2017.
 */

public class images_adapter extends RecyclerView.Adapter<images_view_holder> {

    List<Images_data_model>  _list;
    Activity a ;

    public  images_adapter(List<Images_data_model>  _list , Activity a)
    {
        this._list = _list;
        this.a = a;
    }

    @Override
    public images_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new images_view_holder(LayoutInflater.from(a).inflate(R.layout.images_cell , parent , false));
    }

    @Override
    public void onBindViewHolder(images_view_holder holder, int position) {


            final Images_data_model data_model = _list.get(position);

        Glide.with(a).load(data_model.image).into(holder.img);

        holder.description.setText(data_model.description);



            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent in1 = new Intent(a, View_Image.class);
                    in1.putExtra("image",data_model.image);
                    in1.putExtra("image_id" , data_model.image_id);

                    a.startActivity(in1);


                }
            });


    }

    @Override
    public int getItemCount() {
        return _list.size();
    }


}
