package com.example.god.weatherlayout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Timer;
import java.util.TimerTask;

public class Comments extends AppCompatActivity {

    RecyclerView comments_recycle ;

    List<CommentDataModel> comments_list ;

    private Comment_adapter ad;
    private EditText comment_et ;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        pd = new ProgressDialog(Comments.this);

        pd.setTitle("Loading");

        pd.setMessage("Please wait ..");


        comments_list = new ArrayList<>();

        comments_recycle = (RecyclerView) findViewById(R.id.comment_recycle);

        comment_et = (EditText) findViewById(R.id.comment_et);

        comments_recycle.setLayoutManager(new LinearLayoutManager(Comments.this , LinearLayoutManager.VERTICAL,false));




        get_comment();

    }

    public void close(View view) {

        finish();
    }




   public void send_comment(View v)
   {

       String comment = comment_et.getText().toString();

       if(comment.trim().equalsIgnoreCase(""))
       {
           Toast.makeText(Comments.this , "please write somnething ", Toast.LENGTH_SHORT).show();

           return;
       }

       pd.show();

       String image_id = getIntent().getStringExtra("image_id");

       FirebaseAuth auth = FirebaseAuth.getInstance();

       String email = auth.getInstance().getCurrentUser().getEmail().replace("." ,"");

       FirebaseDatabase database = FirebaseDatabase.getInstance();

       CommentDataModel dataModel = new CommentDataModel();

       dataModel.comment = comment;

       dataModel.email = email;

       SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

       dataModel.name = sp.getString("user_name" , "");



       DatabaseReference reference = database.getReference("comments").child(image_id);

       reference.push().setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {

               pd.hide();
           }
       });




   }


   public void get_comment()
   {

       pd.show();

       FirebaseDatabase database = FirebaseDatabase.getInstance();

       DatabaseReference reference = database.getReference("comments").child(getIntent().getStringExtra("image_id"));

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               pd.hide();

               comments_list.clear();


               for(DataSnapshot snapshot : dataSnapshot.getChildren())
               {

                   CommentDataModel dataModel = snapshot.getValue(CommentDataModel.class);

                   comments_list.add(dataModel);

               }


               ad = new Comment_adapter(comments_list , Comments.this);

               comments_recycle.setAdapter(ad);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


   }


}
