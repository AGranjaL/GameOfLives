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
    private LinkedList<Card> manualCards = new LinkedList<>();
    private LinkedList<Card> autoCards = new LinkedList<>();
    private NumberPicker bid;
    private Card autoCard;
    private int manualBid, autoBid, nCards;
    private int cardsOnTable = 5;
    private int autoBazas,manualBazas = 0;
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


    }
    private void addAnimators(){
        for (int i = 0; i < 2*nCards; i++){
            anim_front.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator));
            anim_back.add((AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator));
        }

    }
    private void setImages(){
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
    private String translateCard(Card card){
        int value = card.getValue();
        char suit = card.getSuit();
        if (value == 10){
            return "s"+suit;
        }
        else if(value == 11){
            return "c"+suit;
        }
        else if (value == 12){
            return "r"+suit;
        }
        else {
            return value+""+suit;
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
    private void flipCard(AnimatorSet anim_back_f, AnimatorSet anim_front_f, ImageView backcard, ImageView frontcard){
        anim_back_f.setTarget(backcard);
        anim_front_f.setTarget(frontcard);
        anim_back_f.start();
        anim_front_f.start();


    }
    //CHANGE FROM LAYOUTS
    private void changeToBid(){
        findViewById(R.id.bidlayout).setVisibility(View.VISIBLE);
        findViewById(R.id.gamelayout).setVisibility(View.INVISIBLE);
    }
    private void changeToGame(){
        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
        findViewById(R.id.bidlayout).setVisibility(View.INVISIBLE);
    }
    //FLIP CARDS
    private void flipAll(){

        for (int i = 0; i < nCards; i++){
            setCard(manualCards.get(i), imagearray_b.get(i));
            setCard(autoCards.get(i), imagearray_b.get(i+5));
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
            flipCard(anim_back.get(i+5), anim_front.get(i+5), imagearray_b.get(i+5), imagearray_f.get(i+5));

        }
    }
    private void flipAllBack(){
        for (int i = 0; i < nCards; i++){
            flipCard(anim_front.get(i), anim_back.get(i), imagearray_f.get(i), imagearray_b.get(i));
            flipCard(anim_front.get(i+5), anim_back.get(i+5), imagearray_f.get(i+5), imagearray_b.get(i+5));
        }
    }
    private void setNotClickable(){
        for (int i = 0; i< cardsOnTable; i++){
            imagearray_b.get(i).setClickable(false);
        }
    }
    private void updateLivesView(){
        TextView autoLives = (TextView) findViewById(R.id.livesauto);
        TextView manualLives = (TextView) findViewById(R.id.livesManual);
        autoLives.setText(""+autoPlayer.getLifes());
        manualLives.setText(""+manualPlayer.getLifes());
    }
    private void removeCardsView(){
        int resource;

        for (int i = 0; i < 2*cardsOnTable; i++) imagearray_b.removeLast().clearColorFilter();

        cardsOnTable--;

        for (int i = 0; i < cardsOnTable; i++) {
            System.out.println("With index " + i + "adding manualCard " + manualCards.get(i));
            resource = getResources().getIdentifier("backcard" + (i + 1), "id", getPackageName());
            setCard(manualCards.get(i), findViewById(resource));
            imagearray_b.add(findViewById(resource));

        }
        for (int i = 0; i < cardsOnTable; i++) {
                System.out.println("With index "+i+"adding autoCard "+ autoCards.get(i));
                resource = getResources().getIdentifier("backcard"+(i+6), "id", getPackageName());
                setCard(autoCards.get(i), findViewById(resource));
                imagearray_b.add(findViewById(resource));
        }
        resource = getResources().getIdentifier("card"+(cardsOnTable+1), "id", getPackageName());
        findViewById(resource).setVisibility(View.GONE);
        resource = getResources().getIdentifier("card"+(cardsOnTable+6), "id", getPackageName());
        findViewById(resource).setVisibility(View.GONE);



    }

    private void manualPlayerThrows(){
        for (int i = 0; i < cardsOnTable; i++){
            imagearray_b.get(i).setClickable(true);
            imagearray_b.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView img = (ImageView) v;
                    String tag = (String) img.getTag();
                    System.out.println("Manual player chose:"+tag);
                    img.setColorFilter(Color.argb(50, 255, 255, 0));
                    setNotClickable();
                    boolean startsManual = !game.isManualHand();
                    System.out.println("WHO STARTS?"+startsManual);
                    manualCard = manualPlayer.throwCard(new Card(tag.charAt(0), tag.charAt(1)));

                    boolean ganaBazaAuto = false;
                    if(startsManual){
                        autoCard = autoPlayer.throwCard();
                        autoPlayerThrows(autoCard);
                        if(manualCard.getValue() >= autoCard.getValue()) ganaBazaAuto = false;
                        else{
                            ganaBazaAuto = true;
                            startsManual = false;
                        }
                    }else{ //auto-player is hand player

                        System.out.println("DOESN'T START MANUAL!!!");
                        if(autoCard.getValue() >= manualCard.getValue()) ganaBazaAuto = true;
                        else{
                            ganaBazaAuto = false;
                            startsManual = true;
                        }
                    }



                    //END OF ROUND
                    if (cardsOnTable == 1){
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

                        if(ganaBazaAuto){
                            autoBazas++;
                            //autoPlayer.setResult(1); //inform autoPlayer result for rewarding
                        }else{
                            manualBazas++;
                            //autoPlayer.setResult(0);
                        }
                        System.out.println("[GAME] Bazas manual Player = " + manualBazas);
                        System.out.println("[GAME] Bazas auto Player = " + autoBazas);
                        System.out.println("[GAME] Vidas manual: " + manualPlayer.getLifes());
                        System.out.println("[GAME] Vidas auto: " + autoPlayer.getLifes());

                        if(nCards==1) nCards = 5;
                        else nCards--;


                        game.setManualHand(!game.isManualHand());


                        System.out.println("---------- GAME FINISHED ----------");
                        System.out.println("   -" + manualPlayer.getName() + " (manual): " + manualPlayer.getLifes() + " lifes.");
                        System.out.println("   -" + autoPlayer.getName() + " (auto): " + autoPlayer.getLifes() + " lifes.");
                        System.out.println("----------------------------------");
                        if(manualPlayer.isAlive()) System.out.println(manualPlayer.getName() + " is the WINNER!!!!");
                        else if(autoPlayer.isAlive()) System.out.println(autoPlayer.getName() + " is the WINNER!!!!");
                        else System.out.println("Oh no! Both players have died"); //SEE IF WE COULD PLAY A MIRROR ROUND TO TIEBRAKE
                        System.out.println("----------------------------------");
                    }else{
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                removeCardsView();
                                playersThrow();


                            }
                        }, 2000);

                    }


                }


            });
        }
    }
    private void autoPlayerThrows(Card autoCard){
        System.out.println("HOLAAAAAAAAAAAAA!!!!!!!");
        for (int j = 0; j < cardsOnTable; j++){
            if (translateCard(autoCard).equals(imagearray_b.get(j+cardsOnTable).getTag())){
                System.out.println("[AUTO] Card found " + translateCard(autoCard));
                imagearray_b.get(j+cardsOnTable).setColorFilter(Color.argb(50, 255, 255, 0));
            }
        }
    }

    public void playersThrow(){
        boolean startsManual = !game.isManualHand();
        if (startsManual) {
            System.out.println("STARTS MANUAL!!");
            manualPlayerThrows();
        } else {
            System.out.println("DOESNT STARTS MANUAL!!");
            autoCard = autoPlayer.throwCard();
            autoPlayerThrows(autoCard);
            manualPlayerThrows();
        }
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.deal_b:
                start();
                break;
            case R.id.button_ok_bid:
                click_ok_button();
                break;
        }

    }
    public void click_ok_button(){
        if(!game.isManualHand()){
            manualBid = manualPlayer.getBid(button_ok,bid,-1);
            autoBid = autoPlayer.getBid(manualBid);
            changeToGame();
        }else{
            manualBid = manualPlayer.getBid(button_ok,bid, autoBid);
            changeToGame();

        }

        flipAll();
        playersThrow();



    }
    public void start(){
        //Start a game
        manualPlayer = game.getManualPlayer();
        autoPlayer = game.getAutoPlayer();
        manualPlayer.setLifes(INIT_LIFES);
        autoPlayer.setLifes(INIT_LIFES);
        nCards = 5; //first round with 5 cards
        //while(manualPlayer.isAlive() && autoPlayer.isAlive()){ //El juego sigue mientras los 2 siguen vivos
            //GAME
            //if(nCards ==1) playMirrorRound();
            //else playRound(nCards);
            playRound();
        //}

    }

    public void playRound(){
        //REPARTIR
        Deck mazo = new Deck();
        mazo.shuffle();
        manualCards = mazo.getNcards(nCards);
        manualPlayer.setCards(manualCards);
        autoCards = mazo.getNcards(nCards);
        autoPlayer.setCards(autoCards);
        bid.setMaxValue(nCards);
        bid.setMinValue(0);
        addAnimators();
        setImages();
        //GET BIDS

        if(!game.isManualHand()){
            changeToBid();
            bid.setOnScrollListener((view, scrollState) -> {
                int bid_v = bid.getValue();
                if(bid_v > manualCards.size()){
                    button_ok.setClickable(false);
                }
                else{
                    button_ok.setClickable(true);
                }
            });


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

        }




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
