package com.example.gamelives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button_to_ins;
    private Button button_to_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        button = (Button) findViewById(R.id.button_id);
        button_to_ins  = (Button) findViewById(R.id.button_instruction);
        button_to_main = (Button) findViewById(R.id.button_to_main);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button pressed");

                // Code here executes on main thread after user presses button
                Intent second_act = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(second_act);
                setContentView(R.layout.activity_second);
            }
        });




    }
    protected void onStart() {
        super.onStart();

        button_to_ins.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                findViewById(R.id.instructions_lay).setVisibility(View.VISIBLE);
                findViewById(R.id.startPage_layout).setVisibility(View.INVISIBLE);




            }
        });
        button_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.startPage_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions_lay).setVisibility(View.INVISIBLE);
            }
        });
    }
}