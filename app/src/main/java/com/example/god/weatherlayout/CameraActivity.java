package com.example.god.weatherlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class CameraActivity extends AppCompatActivity {

    ImageView img;
    private String image_s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        img = (ImageView) findViewById(R.id.cliked_image);


        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            img.setImageBitmap(photo);

            //Getting the Bitmap from Gallery
           image_s = getStringImage(photo);


        }
        else {
            finish();
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    /*public void upload_img(View v)
    {

        Button b = (Button) v;
        b.setEnabled(false);
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

        String userid = sp.getString("user_id" , "");

            JSONObject job = new JSONObject();

            try {
                job.put("image",image_s);
                job.put("user_id" , userid);
                job.put("city",getIntent().getStringExtra("city"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ip_address.ip+"/weather_forecast/upload_image.php", job, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if(response.getString("key").equals("1"))
                        {
                            Toast.makeText(CameraActivity.this , "Uploaded successfully" , Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        else {
                            Toast.makeText(CameraActivity.this , "error" , Toast.LENGTH_SHORT).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)

                {
                    System.out.println(error);
                }
            });

            app_controller.AppController app = new app_controller.AppController(CameraActivity.this);

            app.addToRequestQueue(jobreq);
        }*/




    public void arrow_camera(View v){
        finish();
    }


    // function to convert bitmap to string

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
