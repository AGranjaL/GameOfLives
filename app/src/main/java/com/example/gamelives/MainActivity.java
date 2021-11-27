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
        final Button instr_nextButton = findViewById(R.id.nextButton);
        final CheckBox instructionsCheckBox = findViewById(R.id.checkBox);

        final TextView instr_Overview_TextView = findViewById(R.id.instructions_textView);
        final TextView instr_Game_TextView = findViewById(R.id.instructions_textView2);
        final TextView instr_ValuesAndLives_TextView = findViewById(R.id.instr_ValuesAndLives_TextView);

        final ImageView instr_Overview_ImageView = findViewById(R.id.imageViewInstruct2);
        final ImageView instr_valueAndLives_imageView = findViewById(R.id.instr_valueAndLives_imageView);

        instructionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("InstructionButton pressed");
                instr_Overview_TextView.setVisibility(instr_Overview_TextView.VISIBLE);
                instr_Overview_ImageView.setVisibility(instr_Overview_ImageView.VISIBLE);
                instr_Game_TextView.setVisibility(instr_Game_TextView.VISIBLE);
                instr_nextButton.setVisibility(instr_nextButton.VISIBLE);

                instructionsButton.setVisibility(instructionsButton.INVISIBLE);
                button.setVisibility(button.INVISIBLE);
            }
        });

        instr_nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("NextButton pressed");

                instr_Overview_TextView.setVisibility(instr_Overview_TextView.INVISIBLE);
                instr_Game_TextView.setVisibility(instr_Game_TextView.INVISIBLE);
                instr_Overview_ImageView.setVisibility(instr_Overview_ImageView.INVISIBLE);
                instr_nextButton.setVisibility(instr_nextButton.INVISIBLE);

                instr_ValuesAndLives_TextView.setVisibility(instr_ValuesAndLives_TextView.VISIBLE);
                instr_valueAndLives_imageView.setVisibility(instr_valueAndLives_imageView.VISIBLE);
                instructionsCheckBox.setVisibility(instructionsCheckBox.VISIBLE);

            }
            });


        instructionsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setVisibility(button.VISIBLE);
                instructionsButton.setVisibility(instructionsButton.VISIBLE);

                instr_ValuesAndLives_TextView.setVisibility(instr_ValuesAndLives_TextView.INVISIBLE);
                instr_valueAndLives_imageView.setVisibility(instr_valueAndLives_imageView.INVISIBLE);
                instructionsCheckBox.setVisibility(instructionsCheckBox.INVISIBLE);

            }
        });
    }
}