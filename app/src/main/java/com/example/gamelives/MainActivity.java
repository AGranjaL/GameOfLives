package com.example.gamelives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //String gameState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button pressed");

                // Code here executes on main thread after user presses button
                Intent second_act = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(second_act);
                setContentView(R.layout.activity_second);
            }
        });
        final Button instructionsButton = findViewById(R.id.button_instructions);



    }
}