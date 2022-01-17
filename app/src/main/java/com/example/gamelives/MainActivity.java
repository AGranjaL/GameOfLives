package com.example.gamelives;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private Button button_new;
    private Button button_to_ins;
    private Button button_to_main;
    private Button button_to_next;
    private Button button_to_back;
    private Button button_to_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        button_new = (Button) findViewById(R.id.button_id);
        button_to_ins  = (Button) findViewById(R.id.button_instruction);
        button_to_main = (Button) findViewById(R.id.button_to_main);
        button_to_next = (Button) findViewById(R.id.button_to_next);
        button_to_back = (Button) findViewById(R.id.button_to_back);
        button_to_end = (Button) findViewById(R.id.button_to_end);
        button_new.setOnClickListener(this::onClick);
        button_to_ins.setOnClickListener(this::onClick);
        button_to_main.setOnClickListener(this::onClick);
        button_to_next.setOnClickListener(this::onClick);
        button_to_back.setOnClickListener(this::onClick);
        button_to_end.setOnClickListener(this::onClick);




    }
    protected void onStart() {
        super.onStart();



    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_id:
                Intent second_act = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(second_act);
                setContentView(R.layout.activity_second);
                break;
            case R.id.button_instruction:
                findViewById(R.id.instructions_lay).setVisibility(View.VISIBLE);
                findViewById(R.id.startPage_layout).setVisibility(View.INVISIBLE);
                break;
            case R.id.button_to_main:
                findViewById(R.id.startPage_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions_lay).setVisibility(View.INVISIBLE);
                break;
            case R.id.button_to_next:
                findViewById(R.id.instructions_app_lay).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions_lay).setVisibility(View.INVISIBLE);
                break;
            case R.id.button_to_back:
                findViewById(R.id.instructions_lay).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions_app_lay).setVisibility(View.INVISIBLE);
                break;
            case R.id.button_to_end:
                findViewById(R.id.startPage_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions_app_lay).setVisibility(View.INVISIBLE);
                break;

            }

        }


}