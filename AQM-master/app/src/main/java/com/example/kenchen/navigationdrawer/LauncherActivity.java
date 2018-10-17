package com.example.kenchen.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    //private int SPLASH_TIME = 1000;

    @Override
    //THis whole Activity is for the load screen, trying to figure out a better
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Thread timer = new Thread() {
            public void run() {
                try {
                    //sleep(SPLASH_TIME);
                    setTheme(R.style.AppTheme);
                    Intent homeIntent = new Intent(LauncherActivity.this, LoginActivity.class);
                    startActivity(homeIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
