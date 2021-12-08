package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button, button_ok;
    private int INIT_LIFES = 5;
    private final Semaphore go = new Semaphore(0, true);
    final private LinkedList<ImageView> imagearray_b = new LinkedList<>();
    final private LinkedList<ImageView> imagearray_f = new LinkedList<>();
    final private LinkedList<AnimatorSet> anim_back = new LinkedList<>();
    final private LinkedList<AnimatorSet> anim_front = new LinkedList<>();
    private NumberPicker bid;
    private Game game = new Game();
    private ManualPlayer manualPlayer;
    private AutoPlayer autoPlayer;
    private Card manualCard;
    private boolean isBack = true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button  = (Button) findViewById(R.id.deal_b);
        bid = (NumberPicker) findViewById(R.id.bid);
        button_ok = findViewById(R.id.button_ok_bid);
        //manualPlayer = new ManualPlayer("ManualPlayer");


    }
    @Override
    protected void onStart() {
        super.onStart();
        button.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onResume() {
        super.onResume();

        button.setOnClickListener(this::onClick);
        button_ok.setOnClickListener(this::onClick);
       /* button.setOnClickListener(new View.OnClickListener() {
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
                });*/



    }
    private void addAnimators(int nCards){
        for (int i = 0; i < 2*nCards; i++){
            anim_front.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator));
            anim_back.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator));
        }

    }
    private void setImages(int nCards){
        int resource;
        //manual: 1-5, ia: 6-10
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        for (int i = 1; i < 2*nCards+1; i++){
            resource = getResources().getIdentifier("backcard" + i, "id", getPackageName());
            imagearray_b.add(findViewById(resource));
            imagearray_b.getLast().setCameraDistance(8000 * scale);
            resource = getResources().getIdentifier("frontcard" + i, "id", getPackageName());
            imagearray_f.add( findViewById(resource));
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
    private void flipAll(LinkedList<Card> manualCards, LinkedList<Card> autoCards, int nCards){
        /*for(int i = 0; i < 2*nCards; i++) {
            System.out.println("Flipping cards");
            setCard(cards.get(i), imagearray_b.get(i));
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
            if (i < 5) cardsplayermanual.add(cards.get(i));
            else cardsplayerauto.add(cards.get(i));
        }*/
        for (int i = 0; i < nCards; i++){
            setCard(manualCards.get(i), imagearray_b.get(i));
            setCard(autoCards.get(i), imagearray_b.get(i+5));
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
            flipCard(anim_back.get(i+5), anim_front.get(i+5), imagearray_b.get(i+5), imagearray_f.get(i+5));

        }
    }
    private void flipAllBack(int nCards){
        for (int i = 0; i < nCards; i++){
            flipCard(anim_front.get(i), anim_back.get(i), imagearray_f.get(i), imagearray_b.get(i));
            flipCard(anim_front.get(i+5), anim_back.get(i), imagearray_f.get(i), imagearray_b.get(i));
        }
    }
    private void manualPlayerThrows(int nCards){
        for (int i = 0; i < nCards; i++){
            imagearray_b.get(i).setClickable(true);
            imagearray_b.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView img = (ImageView) v;
                    String tag = (String) img.getTag();
                    System.out.println("Manual player chose:"+tag);
                    img.setColorFilter(Color.argb(50, 255, 255, 0));
                    setNotClickable(nCards);
                    manualCard = manualPlayer.throwCard(new Card(tag.charAt(0), tag.charAt(1)));
                }
            });
        }
    }
    private void autoPlayerThrows(int nCards, Card autoCard){
        for (int j = 0; j < nCards; j++){
            if (autoCard.toString().equals(imagearray_b.get(j+5).getTag())){
                System.out.println("Card found");
                imagearray_b.get(j+5).setColorFilter(Color.argb(50, 255, 255, 0));

            }
        }
    }
    private void setNotClickable(int nCards){
        for (int i = 0; i< nCards; i++){
            imagearray_b.get(i).setClickable(false);
        }
    }
    private void updateLivesView(){
        TextView autoLives = (TextView) findViewById(R.id.livesauto);
        TextView manualLives = (TextView) findViewById(R.id.livesManual);
        autoLives.setText(""+autoPlayer.getLifes());
        manualLives.setText(""+manualPlayer.getLifes());
    }
    private void updateCardsView(int nCards){
        flipAllBack(nCards);
        imagearray_f.get(nCards-1).setVisibility(View.GONE);
        imagearray_f.get(2*nCards-1).setVisibility(View.GONE);
        for (int i = 0; i< 2*nCards; i++){
            imagearray_b.remove(i);
        }
        imagearray_b.get(nCards-1).setVisibility(View.GONE);
        imagearray_b.get(2*nCards-1).setVisibility(View.GONE);
        for (int i = 0; i< 2*nCards; i++){
            imagearray_b.remove(i);
        }

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.deal_b:
                start();
                break;
            case R.id.button_ok_bid:
                //go.release();
                break;
        }

    }
    public void start(){
        //Start a game
        manualPlayer = game.getManualPlayer();
        autoPlayer = game.getAutoPlayer();
        manualPlayer.setLifes(INIT_LIFES);
        autoPlayer.setLifes(INIT_LIFES);
        int nCards = 5; //first round with 5 cards
        while(manualPlayer.isAlive() && autoPlayer.isAlive()){ //El juego sigue mientras los 2 siguen vivos
            //GAME
            //if(nCards ==1) playMirrorRound();
            //else playRound(nCards);
            playRound(nCards);
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
            System.out.println("CHANGE LAYOUT 1");
            bid.setOnScrollListener((view, scrollState) -> {
                int bid_v = bid.getValue();
                if(bid_v > manualCards.size()){
                    button_ok.setClickable(false);
                }
                else{
                    button_ok.setClickable(true);
                }
            });
            try {
                go.acquire();
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
            System.out.println("CHANGE LAYOUT 2");
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
            //try {
            System.out.println("STOPPED");
            while(!button_ok.isPressed());
                //go.acquire();
            System.out.println("HA PASADO POR SEMÁFORO");
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            manualBid = manualPlayer.getBid(button_ok,bid, autoBid);
            changeToGame();
            addAnimators(nCards);
            setImages(nCards);
            flipAll(manualCards, autoCards, nCards);
        }

        int manualBazas = 0;
        int autoBazas = 0;
        boolean startsManual = !game.isManualHand();
        for(int i=0;i<nCards; i++){
            boolean ganaBazaAuto = false;
            if(startsManual){
                manualPlayerThrows(nCards);
                Card autoCard = autoPlayer.throwCard();
                autoPlayerThrows(nCards, autoCard);
                if(manualCard.getValue() >= autoCard.getValue()) ganaBazaAuto = false;
                else{
                    ganaBazaAuto = true;
                    startsManual = false;
                }
            }else{ //auto-player is hand player
                Card autoCard = autoPlayer.throwCard();
                manualPlayerThrows(nCards);
                if(autoCard.getValue() >= manualCard.getValue()) ganaBazaAuto = true;
                else{
                    ganaBazaAuto = false;
                    startsManual = true;
                }
            }
            if(ganaBazaAuto){
                autoBazas++;
                //autoPlayer.setResult(1); //inform autoPlayer result for rewarding
            }else{
                manualBazas++;
                //autoPlayer.setResult(0);
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
        //autoPlayer.setGlobalResult(autoLifesLost);

        System.out.println("MANUAL PLAYER: Puja = " + manualBid + " | Bazas = " + manualBazas + "  => " + manualLifesLost);
        System.out.println("AUTO PLAYER: Puja = " + autoBid + " | Bazas = " + autoBazas + "  => " + autoLifesLost);

        manualPlayer.updateLifes(manualLifesLost);
        autoPlayer.updateLifes(autoLifesLost);
        updateLivesView();
        updateCardsView(nCards);


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
