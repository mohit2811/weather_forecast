package com.example.god.weatherlayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class final_home_page extends FragmentActivity

{

    private static final int PERMISSION_REQUEST_CODE = 200;
    DrawerLayout d;
    static ViewPager pager;


    static List<locationDataModel> locations_list ;

    static   ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_home_page);

        locations_list = new ArrayList<>();

        pd = new ProgressDialog(final_home_page.this);

        pd.setTitle("Loading");
        pd.setMessage("Please wait ..");



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




        if (checkPermission()) {
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }

        get_locations();


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

        List<locationDataModel> loc_list ;

        public MyPagerAdapter(FragmentManager fm , List<locationDataModel> loc_list) {
            super(fm);

            this.loc_list = loc_list;
        }

        @Override
        public Fragment getItem(int pos) {

            if(pos < loc_list.size())
            {

                    Bundle b = new Bundle();

                    b.putString("city", loc_list.get(pos).location);


                    Fragment f = new First_Location();

                    f.setArguments(b);
                    return f;



            }
            return null;
        }

        @Override
        public int getCount() {


            return loc_list.size();
        }
    }



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

        FirebaseAuth.getInstance().signOut();

        SharedPreferences.Editor sp = getSharedPreferences("user_info" , MODE_PRIVATE).edit();

        sp.clear();
        sp.commit();

        Intent i = new Intent(final_home_page.this,sign_in.class);
        startActivity(i);
        finish();
    }



    public static void  remove_loc(final Context context )
    {

        FirebaseAuth auth = FirebaseAuth.getInstance();


        String email = auth.getCurrentUser().getEmail().replace("." , "");



        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String location_id = locations_list.get( pager.getCurrentItem() ).location_id;
        pd.show();

        database.getReference().child("locations").child(email.replace(".","")).child(location_id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {

            public void onComplete(@NonNull Task<Void> task) {

                pd.hide();
                if(task.isSuccessful())
                {

                    Toast.makeText(context , "Location removed" , Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(context , "Error" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    // camera permission code //

    private boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    private void get_locations()
    {

        pd.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String email = auth.getCurrentUser().getEmail().replace("." , "");

        DatabaseReference reference = database.getReference("locations").child(email);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pd.hide();

                locations_list.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    locationDataModel dataModel = dataSnapshot1.getValue(locationDataModel.class);

                    dataModel.location_id = dataSnapshot1.getKey();

                    locations_list.add(dataModel);
                }

                pager.removeAllViews();

                pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager() , locations_list));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
