package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;


import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    //private Integer ListImage[] = {R.drawable.carta1};
    private Button button, button_ok;
    private int dealed_cards = 5;
    private int INIT_LIFES = 5;
    private final Semaphore go = new Semaphore(1, true);
    final private LinkedList<ImageView> imagearray_b = new LinkedList<ImageView>();
    final private LinkedList<ImageView> imagearray_f = new LinkedList<ImageView>();
    final private LinkedList<AnimatorSet> anim_back = new LinkedList<AnimatorSet>();
    final private LinkedList<AnimatorSet> anim_front = new LinkedList<AnimatorSet>();
    final private LinkedList<Card> cardsplayerauto = new LinkedList<Card>();
    final private LinkedList<Card> cardsplayermanual = new LinkedList<Card>();
    private NumberPicker bid;
    private Deck deck;
    private Game game = new Game();
    private int bid_ia, bid_player;
    private ManualPlayer manualPlayer;
    private AutoPlayer autoPlayer;
    private boolean isBack = true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        deck = new Deck();
        //manualPlayer = new ManualPlayer("ManualPlayer");
        manualPlayer = game.getManualPlayer();
        autoPlayer = game.getAutoPlayer();
        button  = (Button) findViewById(R.id.deal_b);
        bid = (NumberPicker) findViewById(R.id.bid);
        button_ok = findViewById(R.id.button_ok_bid);
        button.setOnClickListener(this::onClick);
        button_ok.setOnClickListener(this::onClick);
    }
    @Override
    protected void onStart() {
        super.onStart();
        button.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onResume() {
        super.onResume();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deck.shuffle();
                changeToBid();
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bid_player = manualPlayer.getBid(bid, bid_ia);
                        changeToGame();
                        addAnimators();
                        setImages();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bid_ia = autoPlayer.getBid(5);
                                System.out.println("IA bet"+bid_ia);
                            }
                        }, 2000);

                        LinkedList<Card> cards = deck.getNcards(2*dealed_cards);
                        //animation flipping cards
                        flipAll(cards);
                        autoPlayer.setCards(cardsplayerauto);
                        manualPlayer.setCards(cardsplayermanual);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 5 seconds
                                int selected = 0;
                                Card thrown = autoPlayer.throwCard();
                                for (int i = 0; i < 5; i++){
                                    if (thrown.toString().equals(imagearray_b.get(i+5).getTag())){
                                        System.out.println("Card found");
                                        imagearray_b.get(i+5).setColorFilter(Color.argb(50, 255, 255, 0));

                                    }
                                }
                            }
                        }, 3000);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setClickable();
                            }
                        },3000);

                    }

                });
                    }
                });



    }
    protected void onResume2(){
        super.onResume();
    }
    private void addAnimators(){
        for (int i = 0; i < 2*dealed_cards; i++){
            anim_front.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator));
            anim_back.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator));
        }

    }
    private void setImages(){
        int resource;
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        for (int i = 1; i < 2*dealed_cards+1; i++){
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
    private void changeToBid(){
        findViewById(R.id.bidlayout).setVisibility(View.VISIBLE);
        findViewById(R.id.gamelayout).setVisibility(View.INVISIBLE);
    }
    private void changeToGame(){
        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
        findViewById(R.id.bidlayout).setVisibility(View.INVISIBLE);
    }
    private void flipAll(LinkedList<Card> cards){
        for(int i = 0; i < 2*dealed_cards; i++) {
            System.out.println("Flipping cards");
            setCard(cards.get(i), imagearray_b.get(i));
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
            if (i < 5) cardsplayermanual.add(cards.get(i));
            else cardsplayerauto.add(cards.get(i));
        }
    }
    private void setClickable(){
        for (int i = 0; i < dealed_cards; i++){
            imagearray_b.get(i).setClickable(true);
            imagearray_b.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView img = (ImageView) v;
                    String tag = (String) img.getTag();
                    System.out.println("Manual player chose:"+tag);
                    img.setColorFilter(Color.argb(50, 255, 255, 0));
                    setNotClickable();
                }
            });
        }
    }
    private void setNotClickable(){
        for (int i = 0; i< dealed_cards; i++){
            imagearray_b.get(i).setClickable(false);
        }
    }
    private void dealClick() {
        start();
    }

    private void bidClick() {
        changeToGame();

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.deal_b:


                break;
            case R.id.button_ok_bid:
                go.notify();
                break;
        }

    }
    public void start(){
        //Start a game
        manualPlayer.setLifes(INIT_LIFES);
        autoPlayer.setLifes(INIT_LIFES);
        int nCards = 5; //first round with 5 cards
        while(manualPlayer.isAlive() && autoPlayer.isAlive()){ //El juego sigue mientras los 2 siguen vivos
            //GAME
            if(nCards ==1) playMirrorRound();
            else playRound(nCards);
            System.out.println("[GAME] Vidas manual: " + manualPlayer.getLifes());
            System.out.println("[GAME] Vidas auto: " + autoPlayer.getLifes());

            if(nCards==1) nCards = 5;
            else nCards--;

            game.setManualHand(!game.isManualHand());
        }
        System.out.println("---------- GAME FINISHED ----------");
        System.out.println("   -" + manualPlayer.getName() + " (manual): " + manualPlayer.getLifes() + " lifes.");
        System.out.println("   -" + autoPlayer.getName() + " (auto): " + autoPlayer.getLifes() + " lifes.");
        System.out.println("----------------------------------");
        if(manualPlayer.isAlive()) System.out.println(manualPlayer.getName() + " is the WINNER!!!!");
        else if(autoPlayer.isAlive()) System.out.println(autoPlayer.getName() + " is the WINNER!!!!");
        else System.out.println("Oh no! Both players have died"); //SEE IF WE COULD PLAY A MIRROR ROUND TO TIEBRAKE
        System.out.println("----------------------------------");
    }

    public void playRound(int nCards){
        //REPARTIR
        Deck mazo = new Deck();
        mazo.shuffle();
        LinkedList<Card> manualCards = mazo.getNcards(nCards);
        manualPlayer.setCards(manualCards);
        LinkedList<Card> autoCards = mazo.getNcards(nCards);
        autoPlayer.setCards(autoCards);
        bid.setMaxValue(nCards);
        bid.setMinValue(0);

        //GET BIDS
        int manualBid, autoBid;

        if(!game.isManualHand()){
            changeToBid();
            bid.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    int bid_v = bid.getValue();
                    if(bid_v > manualCards.size()){
                        button_ok.setClickable(false);
                    }
                    else{
                        button_ok.setClickable(true);
                    }
                }
            });
            try {
                go.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manualBid = manualPlayer.getBid(button_ok,bid,-1);
            autoBid = autoPlayer.getBid(manualBid);
            changeToGame();
        }else{
            autoBid = autoPlayer.getBid(-1);
            if(autoBid == nCards) button_ok.setClickable(false);
            changeToBid();
            bid.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    int bid_v = bid.getValue();
                    if((bid_v+autoBid) == manualCards.size() || bid_v > manualCards.size()){
                        button_ok.setClickable(false);
                    }
                    else{
                        button_ok.setClickable(true);
                    }
                }
            });
            try {
                go.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manualBid = manualPlayer.getBid(button_ok,bid, autoBid);
            changeToGame();
        }

        int manualBazas = 0;
        int autoBazas = 0;
        boolean startsManual = !game.isManualHand();
        for(int i=0;i<nCards; i++){
            boolean ganaBazaAuto = false;
            if(startsManual){
                Card manualCard = manualPlayer.throwCard(input, null);
                Card autoCard = autoPlayer.throwCard(0);
                if(manualCard.getValue() >= autoCard.getValue()) ganaBazaAuto = false;
                else{
                    ganaBazaAuto = true;
                    startsManual = false;
                }
            }else{ //auto-player is hand player
                Card autoCard = autoPlayer.throwCard(1);
                Card manualCard = manualPlayer.throwCard(input, autoCard);
                if(autoCard.getValue()>= manualCard.getValue()) ganaBazaAuto = true;
                else{
                    ganaBazaAuto = false;
                    startsManual = true;
                }
            }
            if(ganaBazaAuto){
                autoBazas++;
                autoPlayer.setResult(1); //inform autoPlayer result for rewarding
            }else{
                manualBazas++;
                autoPlayer.setResult(0);
            }
            System.out.println("[GAME] Bazas manual Player = " + manualBazas);
            System.out.println("[GAME] Bazas auto Player = " + autoBazas);
        }

        int manualLifesLost = manualBid - manualBazas;
        if(manualLifesLost < 0) manualLifesLost = manualLifesLost * (-1);
        int autoLifesLost = autoBid - autoBazas;
        if(autoLifesLost < 0) autoLifesLost = autoLifesLost * (-1);
        //if(autoLifesLost==0) autoPlayer.setGlobalResult(1);
        //else autoPlayer.setGlobalResult(0);
        autoPlayer.setGlobalResult(autoLifesLost);

        System.out.println("MANUAL PLAYER: Puja = " + manualBid + " | Bazas = " + manualBazas + "  => " + manualLifesLost);
        System.out.println("AUTO PLAYER: Puja = " + autoBid + " | Bazas = " + autoBazas + "  => " + autoLifesLost);

        manualPlayer.updateLifes(manualLifesLost);
        autoPlayer.updateLifes(autoLifesLost);
    }
    public void playMirrorRound(){
        Deck mazo = new Deck();
        mazo.shuffle();
        Card manualCard = mazo.getNcards(1).getFirst();
        Card autoCard = mazo.getNcards(1).getFirst();
        System.out.println("[GAME] Mirror Round cards: manual=" + manualCard + " auto=" + autoCard);

        //Get mirror bid
        //In this case, as the addition of both bids cannot be 1, the player who is not hand has no decision
        if(game.isManualHand()){
            int manualBid = manualPlayer.getMirrorBid(autoCard);
            System.out.println("[GAME-MirrorRound] Both bid " + manualBid);
            boolean ganaManual = (manualCard.getValue() >= autoCard.getValue());
            if(manualBid==1 && ganaManual) autoPlayer.updateLifes(1); //pierde auto
            else manualPlayer.updateLifes(1); //pierde manual
            game.setManualHand(false);
            return;
        }else{
            int autoBid = autoPlayer.getMirrorBid(manualCard);
            System.out.println("[GAME-MirrorRound] Both bid " + autoBid);
            boolean ganaAuto = (autoCard.getValue() >= manualCard.getValue());
            if(autoBid==1 && ganaAuto) manualPlayer.updateLifes(1);
            else autoPlayer.updateLifes(1);
        }
    }


}
