package com.example.god.weatherlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by GOD on 16-05-2017.
 */

public class images_view_holder extends RecyclerView.ViewHolder {

    ImageView img ;

    TextView description;

    public images_view_holder(View itemView) {
        super(itemView);

        img = (ImageView) itemView.findViewById(R.id.img_id);

        description = itemView.findViewById(R.id.weather_description);
    }
}
