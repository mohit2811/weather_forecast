package com.example.god.weatherlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class final_home_page extends FragmentActivity

{

    DrawerLayout d;
    static ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_home_page);

        ImageView img = (ImageView) findViewById(R.id.default_img);
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

        if( sp.getString("user_id" , "").equals(""))

        {
           img.setVisibility(View.VISIBLE);
        }
        else {
            img.setVisibility(View.GONE);

        }

        d=(DrawerLayout)findViewById(R.id.drawer_layout);


        pager = (ViewPager) findViewById(R.id.viewPager);




    }
    public void sign_in_up_btn(View v){
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

        if( sp.getString("user_id" , "").equals(""))

        {
            Intent i = new Intent(final_home_page.this, sign_in.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(final_home_page.this , "already signed in" , Toast.LENGTH_SHORT).show();

        }
    }

    public void side_button(View v){

        d.openDrawer(Gravity.LEFT);

    }

    public void add_location(View v){

        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);


        if( sp.getString("user_id" , "").equals("")) {

            Toast.makeText(final_home_page.this , "please login to add locations" , Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(final_home_page.this, add_new_location.class);
            startActivity(i);
        }
    }






    public void weather_inf(View v){

        Intent i = new Intent(final_home_page.this,weather_information.class);
        startActivity(i);
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {

        JSONArray jarr ;

        public MyPagerAdapter(FragmentManager fm , JSONArray jarr) {
            super(fm);

            this.jarr = jarr;
        }

        @Override
        public Fragment getItem(int pos) {
            if(pos < jarr.length())
            {
                try {
                    JSONObject job = jarr.getJSONObject(pos);

                    Bundle b = new Bundle();

                    b.putString("city", job.getString("city"));
                    b.putString("loc_id", job.getString("location_id"));

                    Fragment f = new First_Location();

                    f.setArguments(b);
                    return f;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        public int getCount() {
            return jarr.length();
        }
    }

   /* public static void get_locations(final FragmentActivity a)

    {
        SharedPreferences sp = a.getSharedPreferences("user_info" , MODE_PRIVATE);

        String userid = sp.getString("user_id" , "");

        JSONObject jobj = new JSONObject();

        try {
            jobj.put("userid" , userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jobj);
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://"+ip_address.ip+"/weather_forecast/get_locations.php",
                jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray jarr = response.getJSONArray("locations");

                    if(jarr.length() == 0)
                    {
                        Intent i = new Intent(a , add_new_location.class);

                        a.startActivity(i);
                        return;
                    }
                    pager.setAdapter(new MyPagerAdapter(a.getSupportFragmentManager() , jarr));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

            }
        });


        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000, 3 , 2));

        app_controller.AppController app = new app_controller.AppController(a);
        app.addToRequestQueue(jobjreq);
    }*/

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);


        if( ! sp.getString("user_id" , "").equals("")) {

//            get_locations(final_home_page.this);
           }
           else {
            d.openDrawer(Gravity.LEFT);
        }
    }

    public void setting(View v){
        Intent i = new Intent(final_home_page.this,setting.class);
        startActivity(i);
    }

    public void share_button(View v){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
        }
    }

    public void logout(View v){

        SharedPreferences.Editor sp = getSharedPreferences("user_info" , MODE_PRIVATE).edit();

        sp.clear();
        sp.commit();

        Intent i = new Intent(final_home_page.this,sign_in.class);
        startActivity(i);
        finish();
    }



    public void  remove_loc( View v)
    {

      SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);
        String email = sp.getString("user_id" , "");
        String name = sp.getString("user_name" , "");

        createaccount data = new createaccount(name, email,"null");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("user").child(email.replace(".","")).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {

            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Toast.makeText(final_home_page.this , "Location removed" , Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(final_home_page.this , "Error" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
