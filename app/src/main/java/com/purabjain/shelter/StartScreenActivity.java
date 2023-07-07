package com.purabjain.shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent ssIntent = new Intent(StartScreenActivity.this, TaskActivity.class);
                startActivity(ssIntent);
                finish();

            }
        },5000);

    }
}
