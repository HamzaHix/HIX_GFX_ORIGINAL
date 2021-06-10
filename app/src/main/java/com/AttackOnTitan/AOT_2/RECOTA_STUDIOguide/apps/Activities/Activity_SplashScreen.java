package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;

/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
*/

public class Activity_SplashScreen extends AppCompatActivity {

    private int TIME = 9000;
    private TextView textView_1;
    private ImageView imageView_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        textView_1 = findViewById(R.id.textView_1);
        imageView_1 = findViewById(R.id.imageView_1);

        // animation

        textView_1.animate()
                .alpha(1f)
                .setStartDelay(1000)
                .setDuration(7000);

        imageView_1.animate()
                .alpha(1f)
                .setStartDelay(1000)
                .setDuration(7000);

        // This for splashScreen

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent0 = new  Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent0);
                finish();

            }
        },TIME);

    }

}