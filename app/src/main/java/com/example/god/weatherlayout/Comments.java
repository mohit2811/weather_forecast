package com.example.god.weatherlayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Comments extends AppCompatActivity {

    RecyclerView comments_recycle ;
    public  JSONArray jarr ;
    private Comment_adapter ad;
    private EditText comment_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        jarr = new JSONArray();


        comments_recycle = (RecyclerView) findViewById(R.id.comment_recycle);

        comment_et = (EditText) findViewById(R.id.comment_et);

        comments_recycle.setLayoutManager(new LinearLayoutManager(Comments.this , LinearLayoutManager.VERTICAL,false));

        comments_recycle.setAdapter(ad);


        Timer t = new Timer();

//        t.scheduleAtFixedRate(
//                new TimerTask()
//                {
//                    public void run()
//                    {
//                        get_comments();
//                    }
//                },
//                0,      // run first occurrence immediatetly
//                10000);

    }

    public void close(View view) {

        finish();
    }

/*
    public void send_comment(View view) {

        if(comment_et.getText().toString().equals(""))
        {
            return;
        }

        final ImageView img_btn = (ImageView) view;
        img_btn.setEnabled(false);

        JSONObject job = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

        try {
            job.put("event_id" , getIntent().getStringExtra("event_id"));
            job.put("comment" , comment_et.getText().toString());
            job.put("user_id" , sp.getString("user_id", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        comment_et.getText().clear();
        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ip_address.ip+"/events/add_comment.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("done"))
                    {

                        img_btn.setEnabled(true);
                        get_comments();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2 , 2));

        app_controller.AppController app = new app_controller.AppController(Comments.this);
       app.addToRequestQueue(jobreq);

    }
*/

/*
    private void get_comments()
    {
        JSONObject job = new JSONObject();

        try {
            job.put("event_id" , getIntent().getStringExtra("event_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ip_address.ip+"/events/get_comment.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    jarr = response.getJSONArray("result");



                    ad = new Comment_adapter(jarr , Comments.this);

                    comments_recycle.setAdapter(ad);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2 , 2));

        app_controller.AppController app = new app_controller.AppController(Comments.this);
        app.addToRequestQueue(jobreq);

    }
*/
}
