package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends AppCompatActivity {


    //private Integer ListImage[] = {R.drawable.carta1};
    private Button button;
    final private LinkedList<ImageView> imagearray_b = new LinkedList<ImageView>();
    final private LinkedList<ImageView> imagearray_f = new LinkedList<ImageView>();
    private ListIterator list_iter;
    private boolean isBack = true;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //animator for flipping cards
        AnimatorSet anim_front = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator);
        AnimatorSet anim_back = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator);
        AnimatorSet anim_front2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator);
        AnimatorSet anim_back2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator);
        //button to deal cards
        button  = (Button) findViewById(R.id.deal_b);
        int resource;
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        for(int i = 1; i < 3; i++) {
            resource = getResources().getIdentifier("backcard" + i, "id", getPackageName());
            imagearray_b.add((ImageView) findViewById(resource));
            imagearray_b.getLast().setCameraDistance(8000 * scale);
            resource = getResources().getIdentifier("frontcard" + i, "id", getPackageName());
            imagearray_f.add((ImageView) findViewById(resource));
            imagearray_f.getLast().setCameraDistance(8000 * scale);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Deck deck = new Deck();
                deck.shuffle();
                LinkedList<Card> cards = deck.getNcards(2);
                int resource;
                for(int i = 0; i < cards.size(); i++) {
                    resource = getResources().getIdentifier("drawable/carta" + cards.get(i).getValue() + "" + cards.get(i).getSuit(), null, getPackageName());
                    imagearray_b.get(i).setImageResource(resource);
                }
                // Code here executes on main thread after user presses button
                if (isBack){
                    anim_back.setTarget(imagearray_b.get(0));
                    anim_front.setTarget(imagearray_f.get(0));
                    anim_back.start();
                    anim_front.start();
                    anim_back2.setTarget(imagearray_b.get(1));
                    anim_front2.setTarget(imagearray_f.get(1));
                    anim_back2.start();
                    anim_front2.start();

                    isBack = false;

                }
            }
        });
    }
    protected void onStart() {
        super.onStart();



    }
}
