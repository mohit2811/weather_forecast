package com.example.god.weatherlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ghumman on 5/17/2017.
 */

public class Comments_view_holder extends RecyclerView.ViewHolder {

    public TextView user_name , comment ;

    public Comments_view_holder(View itemView) {
        super(itemView);

        user_name = (TextView) itemView.findViewById(R.id.user_name_id);
        comment = (TextView) itemView.findViewById(R.id.comment_id);

    }
}
