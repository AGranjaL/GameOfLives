package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {


    //private Integer ListImage[] = {R.drawable.carta1};
    private boolean isBack = true;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final Button button = (Button) findViewById(R.id.deal_b);
        AnimatorSet anim_front = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator);
        AnimatorSet anim_back = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator);
        ImageView back = (ImageView) findViewById(R.id.backcard1);
        ImageView front = (ImageView) findViewById(R.id.frontcard1);
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        front.setCameraDistance(8000 * scale);
        back.setCameraDistance(8000 * scale);
        button.setOnClickListener(v -> {

        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button pressed");

                // Code here executes on main thread after user presses button
                if (isBack){
                    System.out.println("Gonna flip it");
                    anim_back.setTarget(front);
                    anim_front.setTarget(back);
                    anim_back.start();
                    anim_front.start();
                    isBack = false;

                }
            }
        });
    }
}
