package com.example.gamelives;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button, button_ok;
    private int INIT_LIFES = 5;
    private LinkedList<ImageView> imagearray_b = new LinkedList<>();
    private LinkedList<ImageView> imagearray_f = new LinkedList<>();
    private LinkedList<AnimatorSet> anim_back = new LinkedList<>();
    private LinkedList<AnimatorSet> anim_front = new LinkedList<>();
    private LinkedList<Card> manualCards = new LinkedList<>();
    private LinkedList<Card> autoCards = new LinkedList<>();
    private NumberPicker bid;
    private Card autoCard;
    private int manualBid, autoBid, nCards;
    private int cardsOnTable = 5;
    private int autoBazas,manualBazas = 0;
    private Game game;
    private ManualPlayer manualPlayer;
    private AutoPlayer autoPlayer;
    private Card manualCard;
    private Boolean startsManual;
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
        for (int i = 1; i < nCards+1; i++){
            resource = getResources().getIdentifier("backcard" + i, "id", getPackageName());
            imagearray_b.add(findViewById(resource));
            imagearray_b.getLast().setCameraDistance(8000 * scale);
            resource = getResources().getIdentifier("frontcard" + i, "id", getPackageName());
            imagearray_f.add( findViewById(resource));
            imagearray_f.getLast().setCameraDistance(8000 * scale);
        }
        for (int i = 1; i < nCards+1; i++){
            resource = getResources().getIdentifier("backcard" + (i+5), "id", getPackageName());
            imagearray_b.add(findViewById(resource));
            imagearray_b.getLast().setCameraDistance(8000 * scale);
            resource = getResources().getIdentifier("frontcard" + (i+5), "id", getPackageName());
            imagearray_f.add(findViewById(resource));
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
    private void changeToRes(){
        findViewById(R.id.reslayout).setVisibility(View.VISIBLE);
        findViewById(R.id.gamelayout).setVisibility(View.INVISIBLE);
    }
    private void changeToGame(){
        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
        findViewById(R.id.bidlayout).setVisibility(View.INVISIBLE);
    }
    private void changeToMirror(){
        findViewById(R.id.mirrorlayout).setVisibility(View.VISIBLE);
        findViewById(R.id.gamelayout).setVisibility(View.INVISIBLE);
    }
    //FLIP CARDS
    private void flipAll(){

        for (int i = 0; i < nCards; i++){
            setCard(manualCards.get(i), imagearray_b.get(i));
            setCard(autoCards.get(i), imagearray_b.get(i+nCards));
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_b.get(i), imagearray_f.get(i));
            flipCard(anim_back.get(i+nCards), anim_front.get(i+nCards), imagearray_b.get(i+nCards), imagearray_f.get(i+nCards));

        }
    }
    private void flipAllBack(){
        int resource;
        System.out.println("FLIP ALL BACK");

        imagearray_b.removeLast().clearColorFilter();
        imagearray_b.removeLast().clearColorFilter();

        resource = getResources().getIdentifier("card"+1, "id", getPackageName());
        findViewById(resource).setVisibility(View.GONE);
        resource = getResources().getIdentifier("card"+6, "id", getPackageName());
        findViewById(resource).setVisibility(View.GONE);

        //anim_front = new LinkedList<>();
        //anim_back = new LinkedList<>();
        //addAnimators();

        for (int i = 0; i < nCards; i++) {
            resource = getResources().getIdentifier("card" + (i + 1), "id", getPackageName());
            findViewById(resource).setVisibility(View.VISIBLE);
            resource = getResources().getIdentifier("backcard"+(i + 1), "id", getPackageName());
            flipCard(anim_back.get(i), anim_front.get(i), imagearray_f.get(i), findViewById(resource));
        }
        for (int i = 0; i < nCards; i++) {
            resource = getResources().getIdentifier("card" + (i + 6), "id", getPackageName());
            findViewById(resource).setVisibility(View.VISIBLE);
            resource = getResources().getIdentifier("backcard"+(i + 6), "id", getPackageName());
            flipCard(anim_back.get(i+5), anim_front.get(i+5), imagearray_f.get(i+nCards+1), findViewById(resource));
        }
        cardsOnTable = nCards;

        imagearray_f = new LinkedList<>();

    }
    private void setNotClickable(){
        for (int i = 0; i< cardsOnTable; i++){
            imagearray_b.get(i).setClickable(false);
        }
    }
    private void updateBazasView(){
        TextView autoLives = (TextView) findViewById(R.id.p2score);
        TextView manualLives = (TextView) findViewById(R.id.p1score);
        autoLives.setText("Player2: "+autoBazas);
        manualLives.setText("Player1: "+manualBazas);
    }
    private void updateLivesView(){
        TextView autoLives = (TextView) findViewById(R.id.livesauto);
        TextView manualLives = (TextView) findViewById(R.id.livesManual);
        autoLives.setText(""+autoPlayer.getLifes());
        manualLives.setText(""+manualPlayer.getLifes());
    }
    private void updateBidLayout(){
        int resource;

        if(nCards < 5) {
            resource = getResources().getIdentifier("bcard"+(nCards+1), "id", getPackageName());
            findViewById(resource).setVisibility(View.GONE);
        }
        for (int i = 0; i<nCards; i++){
            resource = getResources().getIdentifier("bfrontcard"+(i+1), "id", getPackageName());
            setCard(manualCards.get(i), findViewById(resource));
        }
    }
    private void updateMirrorLayout(Card autoCard){
        int resource;
        resource = getResources().getIdentifier("mfrontcard", "id", getPackageName());
        setCard(autoCard, findViewById(resource));
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
                    System.out.println("WHO STARTS?"+startsManual);
                    manualCard = manualPlayer.throwCard(new Card(tag.charAt(0), tag.charAt(1)));

                    boolean ganaBazaAuto = false;
                    if(startsManual){
                        autoCard = autoPlayer.throwCard(0, manualCard.getValue());
                        autoPlayerThrows(autoCard);
                        if(manualCard.getValue() >= autoCard.getValue()){
                            ganaBazaAuto = false;
                            manualBazas++;
                            autoPlayer.setResult(0);

                        }
                        else{
                            ganaBazaAuto = true;
                            autoBazas++;
                            autoPlayer.setResult(1);
                            startsManual = false;
                        }

                    }else{ //auto-player is hand player

                        System.out.println("DOESN'T START MANUAL!!!");
                        if(autoCard.getValue() >= manualCard.getValue()){
                            ganaBazaAuto = true;
                            autoBazas++;
                            autoPlayer.setResult(1);

                        }
                        else{
                            ganaBazaAuto = false;
                            manualBazas++;
                            autoPlayer.setResult(0);
                            startsManual = true;
                        }
                    }

                    updateBazasView();

                    //END OF ROUND

                    if (cardsOnTable == 1){
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
                        updateLivesView();

                        System.out.println("[GAME] Bazas manual Player = " + manualBazas);
                        System.out.println("[GAME] Bazas auto Player = " + autoBazas);
                        System.out.println("[GAME] Vidas manual: " + manualPlayer.getLifes());
                        System.out.println("[GAME] Vidas auto: " + autoPlayer.getLifes());
                        if(autoPlayer.isAlive() && manualPlayer.isAlive()){
                            if(nCards==1) nCards = 5;
                            else nCards--;

                            game.setManualHand(!game.isManualHand());

                            if(nCards != 1){
                                flipAllBack();
                                playRound();
                            }
                            else {
                                playMirrorRound();

                            }

                        }
                        else{
                            endOfGame();
                        }


                    }
                    else{
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                removeCardsView();
                                playersThrow();


                            }
                        }, 2000);

                    }

                    /*
                    System.out.println("---------- GAME FINISHED ----------");
                    System.out.println("   -" + manualPlayer.getName() + " (manual): " + manualPlayer.getLifes() + " lifes.");
                    System.out.println("   -" + autoPlayer.getName() + " (auto): " + autoPlayer.getLifes() + " lifes.");
                    System.out.println("----------------------------------");
                    if(manualPlayer.isAlive()) System.out.println(manualPlayer.getName() + " is the WINNER!!!!");
                    else if(autoPlayer.isAlive()) System.out.println(autoPlayer.getName() + " is the WINNER!!!!");
                    else System.out.println("Oh no! Both players have died"); //SEE IF WE COULD PLAY A MIRROR ROUND TO TIEBRAKE
                    System.out.println("----------------------------------");
                    */

                }


            });
        }
    }
    private void endOfGame(){
        if(manualPlayer.isAlive()){
            ((TextView)findViewById(R.id.textResults)).setText(manualPlayer.getName() +" is the WINNER!!!!");

        }
        else if(autoPlayer.isAlive()){
            ((TextView)findViewById(R.id.textResults)).setText(autoPlayer.getName() +" is the WINNER!!!!");

        }
        else System.out.println("Oh no! Both players have died");

        changeToRes();

        System.out.println("---------- GAME FINISHED ----------");
        System.out.println("   -" + manualPlayer.getName() + " (manual): " + manualPlayer.getLifes() + " lifes.");
        System.out.println("   -" + autoPlayer.getName() + " (auto): " + autoPlayer.getLifes() + " lifes.");
        System.out.println("----------------------------------");
        if(manualPlayer.isAlive()) System.out.println(manualPlayer.getName() + " is the WINNER!!!!");
        else if(autoPlayer.isAlive()) System.out.println(autoPlayer.getName() + " is the WINNER!!!!");
        else System.out.println("Oh no! Both players have died"); //SEE IF WE COULD PLAY A MIRROR ROUND TO TIEBREAK
        System.out.println("----------------------------------");
    }
    private void autoPlayerThrows(Card autoCard){

        for (int j = 0; j < cardsOnTable; j++){
            if (translateCard(autoCard).equals(imagearray_b.get(j+cardsOnTable).getTag())){
                System.out.println("[AUTO] Card found " + translateCard(autoCard));
                imagearray_b.get(j+cardsOnTable).setColorFilter(Color.argb(50, 255, 255, 0));
            }
        }
    }

    public void playersThrow(){
        if (startsManual) {
            System.out.println("STARTS MANUAL!!");
            manualPlayerThrows();
        } else {
            System.out.println("DOESNT STARTS MANUAL!!");
            autoCard = autoPlayer.throwCard(1, 13);
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
            autoBid = autoPlayer.getBid(manualBid, 1);
        }else{
            manualBid = manualPlayer.getBid(button_ok,bid, autoBid);


        }
        ((TextView)findViewById(R.id.bidAuto)).setText("AutoPlayer bid: "+ autoBid);
        ((TextView)findViewById(R.id.bidManual)).setText("ManualPlayer bid: "+ manualBid);
        changeToGame();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flipAll();
                playersThrow();

            }
        }, 1000);





    }
    public void start(){
        //Start a game
        game = new Game(getApplicationContext());
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
        manualBazas = 0;
        autoBazas = 0;
        final Boolean[] notified = {false};
        updateBazasView();
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
        updateBidLayout();

        startsManual = !game.isManualHand();
        //GET BIDS

        if(!game.isManualHand()){
            findViewById(R.id.autoplayerBid).setVisibility(View.INVISIBLE);
            changeToBid();
            bid.setOnScrollListener((view, scrollState) -> {
                int bid_v = bid.getValue();
                if(bid_v > nCards){
                    button_ok.setClickable(false);
                }
                else{
                    button_ok.setClickable(true);
                }
            });


        }else{
            autoBid = autoPlayer.getBid(-1, 1);
            if((bid.getValue() + autoBid) == nCards ) button_ok.setClickable(false);
            findViewById(R.id.autoplayerBid).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.autoplayerBid)).setText("AutoPlayer bid: "+autoBid);
            changeToBid();
            bid.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    int bid_v = bid.getValue();
                    if((bid_v+autoBid) == nCards || bid_v > nCards){
                        button_ok.setClickable(false);
                        if(notified[0] == false){
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.bidlayout), "Bid not valid", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            notified[0] = true;
                        }


                    }
                    else{
                        button_ok.setClickable(true);
                        notified[0] = false;
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

        //System.out.println("[GAME] Mirror Round cards: manual=" + manualCard + " auto=" + autoCard);
        updateMirrorLayout(autoCard);
        //Get mirror bid
        //In this case, as the addition of both bids cannot be 1, the player who is not hand has no decision
        if(game.isManualHand()){
            changeToMirror();
            Button win = (Button) findViewById(R.id.win);
            Button lose = (Button) findViewById(R.id.lose);
            win.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int manualBid = 1;
                    boolean ganaManual = (manualCard.getValue() >= autoCard.getValue());
                    if(manualBid==1 && ganaManual) autoPlayer.updateLifes(1); //pierde auto
                    else manualPlayer.updateLifes(1); //pierde manual
                    game.setManualHand(false);
                    if(autoPlayer.isAlive() && manualPlayer.isAlive()) {
                        game.setManualHand(!game.isManualHand());
                        updateBazasView();
                        updateLivesView();
                        nCards = 5;
                        restartCards();
                        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                        findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                        playRound();
                    }else{
                        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                        findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                        endOfGame();
                    }
                }
            });
            lose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int manualBid = 0;
                    boolean ganaManual = (manualCard.getValue() >= autoCard.getValue());
                    if(manualBid==1 && ganaManual) autoPlayer.updateLifes(1); //pierde auto
                    else manualPlayer.updateLifes(1); //pierde manual
                    game.setManualHand(false);
                    if(autoPlayer.isAlive() && manualPlayer.isAlive()) {
                        game.setManualHand(!game.isManualHand());
                        updateBazasView();
                        updateLivesView();
                        nCards = 5;
                        restartCards();
                        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                        findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                        playRound();
                    }else{
                        findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                        findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                        endOfGame();
                    }
                }
            });



        }else{
            int autoBid = autoPlayer.getMirrorBid(manualCard);
            System.out.println("[GAME-MirrorRound] Both bid " + autoBid);
            boolean ganaAuto = (autoCard.getValue() >= manualCard.getValue());
            if(autoBid==1 && ganaAuto) manualPlayer.updateLifes(1);
            else autoPlayer.updateLifes(1);
            if(autoPlayer.isAlive() && manualPlayer.isAlive()) {
                nCards = 5;
                game.setManualHand(!game.isManualHand());
                updateBazasView();
                updateLivesView();
                restartCards();
                findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                playRound();
            }else{
                findViewById(R.id.gamelayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mirrorlayout).setVisibility(View.INVISIBLE);
                endOfGame();
            }
        }
    }

    private void restartCards(){
        int resource;
        int arraysize = imagearray_b.size();
        for(int i =0; i < arraysize; i++){
            imagearray_b.removeLast().clearColorFilter();
        }
        for (int i = 0; i < nCards; i++) {
            resource = getResources().getIdentifier("card" + (i + 1), "id", getPackageName());
            findViewById(resource).setVisibility(View.VISIBLE);
            resource = getResources().getIdentifier("backcard"+(i + 1), "id", getPackageName());
            findViewById(resource).setAlpha(0);
            resource = getResources().getIdentifier("frontcard"+(i + 1), "id", getPackageName());
            findViewById(resource).setAlpha(1);

        }
        for (int i = 0; i < nCards; i++) {
            resource = getResources().getIdentifier("card" + (i + 6), "id", getPackageName());
            findViewById(resource).setVisibility(View.VISIBLE);
            resource = getResources().getIdentifier("backcard"+(i + 6), "id", getPackageName());
            findViewById(resource).setAlpha(0);
            resource = getResources().getIdentifier("frontcard"+(i + 6), "id", getPackageName());
            findViewById(resource).setAlpha(1);
        }
        cardsOnTable = nCards;
        for (int i = 0; i<nCards; i++){
            resource = getResources().getIdentifier("bcard" + (i + 1), "id", getPackageName());
            findViewById(resource).setVisibility(View.VISIBLE);
        }
        imagearray_f = new LinkedList<>();
        imagearray_b = new LinkedList<>();
    }
}
