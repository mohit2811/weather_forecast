package com.example.god.weatherlayout;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ghumman on 5/17/2017.
 */

public class Comment_adapter extends RecyclerView.Adapter<Comments_view_holder> {

    JSONArray jarr ;
    Activity a;

    public Comment_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr ;
        this.a = a;
    }
    @Override
    public Comments_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Comments_view_holder(LayoutInflater.from(a).inflate(R.layout.comment_cell,parent, false));

    }

    @Override
    public void onBindViewHolder(Comments_view_holder holder, int position) {

        try {
            JSONObject job = jarr.getJSONObject(position);
            holder.user_name.setText(job.getString("Name"));
            holder.comment.setText(job.getString("Comment"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
