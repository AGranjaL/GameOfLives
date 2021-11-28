package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    final private LinkedList<Card> cardsplayerauto = new LinkedList<Card>();
    final private LinkedList<Card> cardsplayermanual = new LinkedList<Card>();
    private Deck deck;
    private ManualPlayer manualPlayer;
    private AutoPlayer ia;
    private boolean isBack = true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        deck = new Deck();
        manualPlayer = new ManualPlayer("ManualPlayer");
        ia = new AutoPlayer("IA");
        button  = (Button) findViewById(R.id.deal_b);
        //animator for flipping cards



        /*ImageView image = imagearray_b.getLast();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setColorFilter(Color.argb(50, 255, 255, 0));
            }
        });*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        button.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onResume() {
        super.onResume();
        addAnimators();
        //button to deal cards

        setImages();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deck.shuffle();
                LinkedList<Card> cards = deck.getNcards(dealed_cards);
                for(int i = 0; i < dealed_cards; i++) {
                    setCard(cards.get(i), imagearray_b.get(i));
                    flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
                    if (i < 5) cardsplayermanual.add(cards.get(i));
                    else cardsplayerauto.add(cards.get(i));

                }
                ia.setCards(cardsplayerauto);
                manualPlayer.setCards(cardsplayermanual);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 5 seconds
                        int selected = 0;
                        Card thrown = ia.throwCard();
                        for (int i = 0; i < 5; i++){
                            if (thrown.toString().equals(imagearray_b.get(i+5).getTag())){
                                System.out.println("Card found");
                                imagearray_b.get(i+5).setColorFilter(Color.argb(50, 255, 255, 0));
                            }
                        }
                    }
                }, 3000);

            }

        });

        //ImageView selected_v = imagearray_b.get(selected+5);
        //selected_v.setColorFilter(Color.argb(50, 255, 255, 0));




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
            image.setTag("s"+suit);
        }
        else if(value == 11){
            resource = getResources().getIdentifier("drawable/cartac"+suit, null, getPackageName());
            image.setTag("c"+suit);
        }
        else if (value == 12){
            resource = getResources().getIdentifier("drawable/cartar"+ suit, null, getPackageName());
            image.setTag("r"+suit);
        }
        else {
            resource = getResources().getIdentifier("drawable/carta"+ value + "" + suit, null, getPackageName());
            image.setTag(value+""+suit);
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
