package com.example.gamelives;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import javax.security.auth.callback.Callback;

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
        final TextView instructionsTextView = findViewById(R.id.instructions_textView);
        final CheckBox instructionsCheckBox = findViewById(R.id.checkBox);

        instructionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("InstructionButton pressed");
                instructionsCheckBox.setVisibility(instructionsCheckBox.VISIBLE);
                instructionsTextView.setVisibility(instructionsTextView.VISIBLE);
                instructionsButton.setVisibility(instructionsButton.INVISIBLE);
                button.setVisibility(button.INVISIBLE);
                /*if (instructionsCheckBox.isChecked());{
                    instructionsTextView.
                }
                    //setContentView(R.layout.activity_main);
                //    instructionsButton.setVisibility(instructionsButton.VISIBLE);
                  //  instructionsTextView.setVisibility(instructionsTextView.INVISIBLE);
                }*/
            }
        });

        instructionsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionsTextView.setVisibility(instructionsTextView.INVISIBLE);
                button.setVisibility(button.VISIBLE);
                instructionsButton.setVisibility(instructionsButton.VISIBLE);

            }
        });
    }
}