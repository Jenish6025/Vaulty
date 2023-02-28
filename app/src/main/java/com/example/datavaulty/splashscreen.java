package com.example.datavaulty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

public class splashscreen extends AppCompatActivity {

    RoundedHorizontalProgressBar progressmate;
    SharedPreferences sharedpreferences1;

    SharedPreferences sharedPreferences;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences1= getSharedPreferences("password", Context.MODE_PRIVATE);
        final String check_pin=sharedpreferences1.getString("password","no");

        Log.e("---->",""+check_pin);


        progressmate=findViewById(R.id.progressmate);
        progressmate.animateProgress(4000, 0, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (check_pin=="no") {

                    Intent intent=new Intent(splashscreen.this,dialer.class);
                    intent.putExtra("changepin","no");
                    startActivity(intent);
                    // startActivity(new Intent(splashscreen.this,dialer.class));
                    finish();

                }
                else{
                    startActivity(new Intent(splashscreen.this,dialer_new.class));
                    finish();
                }

            }
        },4000);

    }
}
