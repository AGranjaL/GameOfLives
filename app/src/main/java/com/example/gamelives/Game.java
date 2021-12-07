package com.example.gamelives;

import java.util.*;

public class Game{
    private static final int INIT_LIFES = 5;
    //private LinkedList<Player> players;
    private ManualPlayer manualPlayer;
    private AutoPlayer autoPlayer;
    private boolean isManualHand; //true if hand player is the manualplayer
    //private Scanner input;

    //CONSTRUCTORS
    public Game(String manualPlayer, String autoPlayer//,Scanner sc
                 ) {
        this.manualPlayer = new ManualPlayer(manualPlayer);
        this.autoPlayer = new AutoPlayer(autoPlayer);
        isManualHand = true; //First hand player is manualPlayer
        //input = sc;
    }

    public Game(
            //Scanner sc
    ){
        //Default players
        manualPlayer = new ManualPlayer("manualPlayer");
        autoPlayer = new AutoPlayer("autoPlayer");
        isManualHand = true;
    }

    //GETTERS
    public AutoPlayer getAutoPlayer() {
        return autoPlayer;
    }

    public ManualPlayer getManualPlayer(){
        return manualPlayer;
    }

    public ArrayList<Player> getPlayers(){
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(manualPlayer);
        players.add(autoPlayer);
        return players;
    }

    public boolean isManualHand() {
        return isManualHand;
    }

    //SETTER
    public void setManualHand(boolean isManualHand) {
        this.isManualHand = isManualHand;
        return;
    }

    //START
    /*public void start(){
        //Start a game
        manualPlayer.setLifes(INIT_LIFES);
        autoPlayer.setLifes(INIT_LIFES);
        int nCards = 2; //first round with 5 cards
        while(manualPlayer.isAlive() && autoPlayer.isAlive()){ //El juego sigue mientras los 2 siguen vivos
            //GAME
            if(nCards ==1) playMirrorRound();
            else playRound(nCards);
            System.out.println("[GAME] Vidas manual: " + manualPlayer.getLifes());
            System.out.println("[GAME] Vidas auto: " + autoPlayer.getLifes());

            if(nCards==1) nCards = 5;
            else nCards--;

            isManualHand= !isManualHand;
        }
        System.out.println("---------- GAME FINISHED ----------");
        System.out.println("   -" + manualPlayer.getName() + " (manual): " + manualPlayer.getLifes() + " lifes.");
        System.out.println("   -" + autoPlayer.getName() + " (auto): " + autoPlayer.getLifes() + " lifes.");
        System.out.println("----------------------------------");
        if(manualPlayer.isAlive()) System.out.println(manualPlayer.getName() + " is the WINNER!!!!");
        else if(autoPlayer.isAlive()) System.out.println(autoPlayer.getName() + " is the WINNER!!!!");
        else System.out.println("Oh no! Both players have died"); //SEE IF WE COULD PLAY A MIRROR ROUND TO TIEBRAKE
        System.out.println("----------------------------------");
    }*/

    /*public void playRound(int nCards){
        //REPARTIR
        Deck mazo = new Deck();
        mazo.shuffle();
        LinkedList<Card> cards = mazo.getNcards(nCards);
        manualPlayer.setCards(cards);
        cards = mazo.getNcards(nCards);
        autoPlayer.setCards(cards);

        //GET BIDS
        int manualBid, autoBid;
        if(!isManualHand){
            manualBid = manualPlayer.getBid(input, -1);
            autoBid = autoPlayer.getBid(manualBid);
        }else{
            autoBid = autoPlayer.getBid(-1);
            manualBid = manualPlayer.getBid(input, autoBid);
        }

        int manualBazas = 0;
        int autoBazas = 0;
        boolean startsManual = !isManualHand;
        for(int i=0;i<nCards; i++){
            if(startsManual){
                Card manualCard = manualPlayer.throwCard(input, null);
                Card autoCard = autoPlayer.throwCard(manualCard);
                if(manualCard.getValue() >= autoCard.getValue()) manualBazas++;
                else{
                    autoBazas++;
                    startsManual = false;
                }
            }else{ //auto-player is hand player
                Card autoCard = autoPlayer.throwCard(null);
                Card manualCard = manualPlayer.throwCard(input, autoCard);
                if(autoCard.getValue()>= manualCard.getValue()) autoBazas++;
                else{
                    manualBazas++;
                    startsManual = true;
                }
            }
            System.out.println("[GAME] Bazas manual Player = " + manualBazas);
            System.out.println("[GAME] Bazas auto Player = " + autoBazas);
        }

        int manualLifesLost = manualBid - manualBazas;
        if(manualLifesLost < 0) manualLifesLost = manualLifesLost * (-1);
        int autoLifesLost = autoBid - autoBazas;
        if(autoLifesLost < 0) autoLifesLost = autoLifesLost * (-1);

        System.out.println("MANUAL PLAYER: Puja = " + manualBid + " | Bazas = " + manualBazas + "  => " + manualLifesLost);
        System.out.println("AUTO PLAYER: Puja = " + autoBid + " | Bazas = " + autoBazas + "  => " + autoLifesLost);

        manualPlayer.updateLifes(manualLifesLost);
        autoPlayer.updateLifes(autoLifesLost);
    }*/

    /*public void playMirrorRound(){
        Deck mazo = new Deck();
        mazo.shuffle();
        Card manualCard = mazo.getNcards(1).getFirst();
        Card autoCard = mazo.getNcards(1).getFirst();
        System.out.println("[GAME] Mirror Round cards: manual=" + manualCard + " auto=" + autoCard);

        //Get mirror bid
        //In this case, as the addition of both bids cannot be 1, the player who is not hand has no decision
        if(isManualHand){
            int manualBid = manualPlayer.getMirrorBid(input, autoCard);
            System.out.println("[GAME-MirrorRound] Both bid " + manualBid);
            boolean ganaManual = (manualCard.getValue() >= autoCard.getValue());
            if(manualBid==1 && ganaManual) autoPlayer.updateLifes(1); //pierde auto
            else manualPlayer.updateLifes(1); //pierde manual
            isManualHand = false;
            return;
        }else{
            int autoBid = autoPlayer.getMirrorBid(manualCard);
            System.out.println("[GAME-MirrorRound] Both bid " + autoBid);
            boolean ganaAuto = (autoCard.getValue() >= manualCard.getValue());
            if(autoBid==1 && ganaAuto) manualPlayer.updateLifes(1);
            else autoPlayer.updateLifes(1);
        }
    }*/
}