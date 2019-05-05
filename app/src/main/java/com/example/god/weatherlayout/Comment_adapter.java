package com.example.god.weatherlayout;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ghumman on 5/17/2017.
 */

public class Comment_adapter extends RecyclerView.Adapter<Comments_view_holder> {

    List<CommentDataModel> comment_list ;

    Activity a;

    public Comment_adapter(List<CommentDataModel> comment_list , Activity a)
    {
        this.comment_list = comment_list;
        this.a = a;
    }
    @Override
    public Comments_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Comments_view_holder(LayoutInflater.from(a).inflate(R.layout.comment_cell,parent, false));

    }

    @Override
    public void onBindViewHolder(Comments_view_holder holder, int position) {

        CommentDataModel dataModel = comment_list.get(position);


            holder.user_name.setText(dataModel.name);
            holder.comment.setText(dataModel.comment);

    }

    @Override
    public int getItemCount() {
        return comment_list.size();
    }
}
