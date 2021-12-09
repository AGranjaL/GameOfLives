package com.example.gamelives;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.NumberPicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class BidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_layout);

        NumberPicker np = findViewById(R.id.bid);

        np.setMinValue(0);
        np.setMinValue(20);

        np.setOnValueChangedListener(onValueChangeListener);
    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            Toast.makeText(BidActivity.this, "selected number" +bid.getValue(), Toast.LENGTH_SHORT);
        }
    };
/*
    Handler handler = new Handler();
        handler.postDelayed(new

    Runnable() {
        @Override
        public void run () {
            System.out.println("HOLA");
        }
    },4000);
    Intent second_act = new Intent(BidActivity.this, SecondActivity.class);

    startActivity(second_act);

    setContentView(R.layout.activity_second);


 */
}
