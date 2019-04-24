package com.example.god.weatherlayout;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class pop_up extends Dialog {

    Button c_btn , f_btn ;

    Context c ;

    public pop_up(Context context) {
        super(context);

        c = context ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        c_btn = (Button) findViewById(R.id.c_button);

        f_btn = (Button) findViewById(R.id.f_button);

        c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor sp = c.getSharedPreferences("settings" , Context.MODE_PRIVATE).edit();

                sp.putString("temp_unit","C");
                sp.commit();
                setting.temp.setText("C");
                dismiss();
            }
        });

        f_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor sp = c.getSharedPreferences("settings" , Context.MODE_PRIVATE).edit();

                sp.putString("temp_unit","F");
                sp.commit();
                setting.temp.setText("F");
                dismiss();
            }
        });
    }
}