package com.example.god.weatherlayout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class sign_up extends AppCompatActivity {

    private EditText name_et , email_et, password_et , confirm_password_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name_et = (EditText) findViewById(R.id.name);
        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        confirm_password_et = (EditText) findViewById(R.id.confirm_password);
    }


    public void arrow_sign_up(View v){
        finish();
    }

    public void submit_button(View v){
        final String name = name_et.getText().toString();
        final String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        String confirm_password = confirm_password_et.getText().toString();

        if(name.equals("")) {

            Toast.makeText(sign_up.this , "enter your name" , Toast.LENGTH_SHORT).show();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Toast.makeText(sign_up.this, "enter your email", Toast.LENGTH_SHORT).show();
            return;
        }


            String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";




            if(!password.matches(pattern) || password.length() < 8)
            {
                Toast.makeText(sign_up.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
                return;
            }
            if (! confirm_password.equals(password))
            {

                Toast.makeText( sign_up.this ,"password do not match", Toast.LENGTH_SHORT).show();
                return;

            }

        final ProgressDialog progress_bar = new ProgressDialog(sign_up.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if (task.isSuccessful()) {
                    Toast.makeText(sign_up.this, "done", Toast.LENGTH_SHORT).show();
                    createaccount data = new createaccount(name, email,"null");
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("user").child(email.replace(".","")).setValue(data);
                    finish();

                } else {
                    Toast.makeText(sign_up.this, "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);


    }

    public void sign_in(View v){
        Intent i = new Intent(sign_up.this,sign_in.class);
        startActivity(i);
    }

}
