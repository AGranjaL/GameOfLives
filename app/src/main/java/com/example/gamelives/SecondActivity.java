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
    private int dealed_cards = 10;
    final private LinkedList<ImageView> imagearray_b = new LinkedList<ImageView>();
    final private LinkedList<ImageView> imagearray_f = new LinkedList<ImageView>();
    final private LinkedList<AnimatorSet> anim_back = new LinkedList<AnimatorSet>();
    final private LinkedList<AnimatorSet> anim_front = new LinkedList<AnimatorSet>();
    private boolean isBack = true;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Deck deck = new Deck();
        //animator for flipping cards
        addAnimators();
        //button to deal cards
        button  = (Button) findViewById(R.id.deal_b);
        setImages();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deck.shuffle();
                LinkedList<Card> cards = deck.getNcards(dealed_cards);
                for(int i = 0; i < dealed_cards; i++) {
                    setCard(cards.get(i), imagearray_b.get(i));
                    flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
                }

            }
        });
    }
    protected void onStart() {
        super.onStart();
        button.setVisibility(View.VISIBLE);


    }
    private void addAnimators(){
        for (int i = 0; i < dealed_cards; i++){
            anim_front.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator));
            anim_back.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator));
        }

    }
    private void setImages(){
        int resource;
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        for (int i = 1; i < dealed_cards+1; i++){
            resource = getResources().getIdentifier("backcard" + i, "id", getPackageName());
            imagearray_b.add((ImageView) findViewById(resource));
            imagearray_b.getLast().setCameraDistance(8000 * scale);
            resource = getResources().getIdentifier("frontcard" + i, "id", getPackageName());
            imagearray_f.add((ImageView) findViewById(resource));
            imagearray_f.getLast().setCameraDistance(8000 * scale);
        }
    }
    private void setCard(Card card, ImageView image){
        int value = card.getValue();
        char suit = card.getSuit();
        int resource;
        if (value == 10){
            resource = getResources().getIdentifier("drawable/cartas"+suit, null, getPackageName());
        }
        else if(value == 11){
            resource = getResources().getIdentifier("drawable/cartac"+suit, null, getPackageName());
        }
        else if (value == 12){
            resource = getResources().getIdentifier("drawable/cartar"+ suit, null, getPackageName());
        }
        else {
            resource = getResources().getIdentifier("drawable/carta"+ value + "" + suit, null, getPackageName());
        }
        image.setImageResource(resource);
    }
    private void flipCard(AnimatorSet anim_back, AnimatorSet anim_front, ImageView backcard, ImageView frontcard){
        anim_back.setTarget(backcard);
        anim_front.setTarget(frontcard);
        anim_back.start();
        anim_front.start();


    }
}
