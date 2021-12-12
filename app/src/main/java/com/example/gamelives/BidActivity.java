package com.example.gamelives;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

public class BidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_layout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("HOLA");
            }
        }, 4000);
        Intent second_act = new Intent(BidActivity.this, SecondActivity.class);
        startActivity(second_act);
        setContentView(R.layout.activity_second);

    }
}
