package com.example.god.weatherlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class sign_in extends AppCompatActivity {


    EditText email_et , password_et;
    TextView show_pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        show_pswd = (TextView) findViewById(R.id.show_pswrd);

        show_pswd.setVisibility(View.GONE);

        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password_et.getText().length()>0){
                    show_pswd.setVisibility(View.VISIBLE);
                }
                else {
                    show_pswd.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        show_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_pswd.getText()=="SHOW"){
                    show_pswd.setText("HIDE");
                    password_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password_et.setSelection(password_et.length());
                }
                else{
                    show_pswd.setText("SHOW");
                    password_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_et.setSelection(password_et.length());
                }
            }
        });


    }

    public void arrow_sign_in(View v){
      finish();
    }


    public void login(View v)
    {
        final String email = email_et.getText().toString();

        String password = password_et.getText().toString();

        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";




        if(!password.matches(pattern) || password.length() < 8)
        {
            Toast.makeText(sign_in.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(sign_in.this , "enter your email" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(""))
        {
            Toast.makeText(sign_in.this , "enter your password" , Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    FirebaseDatabase data = FirebaseDatabase.getInstance();

                    data.getReference().child("user").child(email.replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        createaccount cc=dataSnapshot.getValue(createaccount.class);
                            SharedPreferences.Editor sp = getSharedPreferences("user_info" , MODE_PRIVATE).edit();

                            sp.putString("user_id" ,cc.email);
                            sp.putString("user_name" ,cc.name);
                            sp.putString("user_loc" ,cc.loc);

                            sp.commit();
                            Intent i = new Intent(sign_in.this,final_home_page.class);
                            startActivity(i);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });

                }
                else {

                    Toast.makeText(sign_in.this , "invalid credentials" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void sign_upButton(View view) {
        Intent i = new Intent(sign_in.this,sign_up.class);
        startActivity(i);
    }

    public void forgot_pswd(View v){
        Intent i = new Intent(sign_in.this,forgot_password.class);
        startActivity(i);
    }
}