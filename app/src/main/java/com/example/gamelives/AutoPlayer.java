package com.example.gamelives;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Map.Entry;


public class AutoPlayer extends Player{

    private double learning_rate_win = 0.1;
    private double ponderacion = 0.9;

    private HashMap<String, Double[]> pujasIA5 = new HashMap<>();       // cambiar el double por una lista de 6 valores, más fácil de acceder
    private HashMap<String, Double[]> pujasIA4 = new HashMap<>();
    private HashMap<String, Double[]> pujasIA3 = new HashMap<>();
    private HashMap<String, Double[]> pujasIA2 = new HashMap<>();
    private HashMap<String, Double[]> pujasIA1 = new HashMap<>();

    private HashMap<String, Double[]> juegoIA5 = new HashMap<>();       // La clave sera el estado, y el segundo valor sera la recompensa
    private HashMap<String, Double[]> juegoIA4 = new HashMap<>();
    private HashMap<String, Double[]> juegoIA3 = new HashMap<>();
    private HashMap<String, Double[]> juegoIA2 = new HashMap<>();
    private HashMap<String, Double[]> juegoIA1 = new HashMap<>();
    private HashMap<String, Integer[]> estadosRonda = new HashMap<>();
    private int[] countCards = new int[10];                             // Here we will have the number of cards of each type
    private int numCards;
    private int totalCards;
    private int[] firstTimeBids = {0, 0, 0, 0, 0};
    private int[] firstTimeCards = {0, 0, 0, 0, 0};
    private int bid = 0;
    private int bid_aux = 0;
    private Card card;
    private String roundState;                   // It saves the actual state in which we are at the moment of throw a card to update the rewards of this state
    private String roundStateBids;               // It saves the actual state in which we are at the moment of select a bid to update the rewards of this state
    private String trainString = "";

    public AutoPlayer(String name){
        super(name);
    }

    public AutoPlayer(int train){
        //ENTRENAMIENTO

        super("IA");

        if (train ==1){
            trainString = "trainedMaps";
            initMaps(train);
        } else if (train == 0) {
            trainString = "NoTrainedMaps";
            initMaps(train);
        } else if (train == 2){
            trainString = "trainedMaps";
            initMaps(train);
        }


    }

    private void initMaps(int t) {

        System.out.println("Inicializo mapas");
        if (t != 2){
            for (int i = 0; i< firstTimeBids.length; i++) {
                firstTimeBids[i] = 1;
            }
            for (int i = 0; i< firstTimeCards.length; i++) {
                firstTimeCards[i] = 1;
            }
        }

        String state = "";
        int numCards = 0;

        /*for (int ones = 0; ones <= 4; ones++) {
            for (int twos = 0; twos <= 4; twos++) {
                for (int threes = 0; threes <=  4; threes++) {
                    for (int fours = 0; fours <= 4; fours++) {
                        for (int fives = 0; fives <= 4; fives++) {
                            for (int sixs = 0; sixs <= 4; sixs++) {
                                for (int sevens = 0; sevens <= 4 ; sevens++) {
                                    for (int jacks = 0; jacks <= 4 ; jacks++) {
                                        for (int horses = 0; horses <= 4; horses++) {
                                            for (int kings = 0; kings <= 4; kings++) {
                                                for (int i = -1; i <= 5; i++) {              // Num de pujas del otro de [0,5], -1 si soy el primero en pujar
                                                    state = "" + ones + twos + threes + fours + fives + sixs + sevens + jacks + horses + kings + i;
                                                    numCards = ones + twos + threes + fours + fives + sixs + sevens + jacks + horses + kings;
                                                    if (numCards == 5) {
                                                        Double[] initialization5 = { (double) 1/6, (double) 1/6, (double) 1/6, (double) 1/6, (double) 1/6, (double) 1/6};
                                                        pujasIA5.put(state, initialization5);
                                                    } else if (numCards == 4 && i <= numCards) {
                                                        Double[] initialization4 = { 0.2, 0.2, 0.2, 0.2, 0.2};
                                                        pujasIA4.put(state, initialization4);
                                                    } else if (numCards == 3 && i <= numCards) {
                                                        Double[] initialization3 = { 0.25, 0.25, 0.25, 0.25};
                                                        pujasIA3.put(state, initialization3);
                                                    } else if (numCards == 2 && i <= numCards) {
                                                        Double[] initialization2 = { (double) 1/3, (double) 1/3, (double) 1/3};
                                                        pujasIA2.put(state, initialization2);
                                                    } else if (numCards == 1 && i <= numCards) {
                                                        Double[] initialization1 = { 0.5, 0.5};
                                                        pujasIA1.put(state, initialization1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int opponentCard = -1;
        for (int ones = 0; ones <= 4; ones++) {
            for (int twos = 0; twos <= 4; twos++) {
                for (int threes = 0; threes <=  4; threes++) {
                    for (int fours = 0; fours <= 4; fours++) {
                        for (int fives = 0; fives <= 4; fives++) {
                            for (int sixs = 0; sixs <= 4; sixs++) {
                                for (int sevens = 0; sevens <= 4 ; sevens++) {
                                    for (int jacks = 0; jacks <= 4 ; jacks++) {
                                        for (int horses = 0; horses <= 4; horses++) {
                                            for (int kings = 0; kings <= 4; kings++) {
                                                for (int numBids = -5; numBids <= 5; numBids++) {        // Num de bazas que nos quedan por ganar, negativo si hemos ganado de mas
                                                    for (int e = 0; e <= 1 ; e++) {                      // Nos dice si somos los primeros en tirar
                                                        for (opponentCard = 0; opponentCard < 4 ; opponentCard++) {
                                                            if (e == 1) opponentCard = 13;
                                                            state = "" + ones + twos + threes + fours + fives + sixs + sevens + jacks + horses + kings + numBids + e + opponentCard;
                                                            numCards = ones + twos + threes + fours + fives + sixs + sevens + jacks + horses + kings;

                                                            Double[] initializationCards = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
                                                            /* double sum = 0;
                                                            Double[] init_cards = {(double)ones, (double)twos, (double)threes, (double)fours, (double)fives,
                                                                                   (double)sixs, (double)sevens, (double)jacks, (double)horses, (double)kings};

                                                            for (int i = 0; i < init_cards.length; i++) {
                                                                if (init_cards[i] == 0){
                                                                    initializationCards[i] = 0.0;
                                                                } else {
                                                                    sum = sum+initializationCards[i];
                                                                }
                                                            }

                                                            for (int i = 0; i < initializationCards.length; i++){
                                                                if (initializationCards[i] != 0.0){
                                                                    initializationCards[i] = initializationCards[i]/sum;
                                                                }

                                                            } */
                                                            /*if (numCards == 5 && numBids >= 0) {
                                                                juegoIA5.put(state, initializationCards);
                                                            } else if (numCards == 4 && numBids >= -1) {
                                                                juegoIA4.put(state, initializationCards);
                                                            } else if (numCards == 3 && numBids >= -2) {
                                                                juegoIA3.put(state, initializationCards);
                                                            } else if (numCards == 2 && numBids >= -3) {
                                                                juegoIA2.put(state, initializationCards);
                                                            } else if (numCards == 1 && numBids >= -4) {
                                                                juegoIA1.put(state, initializationCards);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
        System.out.println("Mapa 5 " + juegoIA5.size());
        System.out.println("Mapa 4 " + juegoIA4.size());
        System.out.println("Mapa 3 " + juegoIA3.size());
        System.out.println("Mapa 2 " + juegoIA2.size());
        System.out.println("Mapa 1 " + juegoIA1.size());
    }



    private void countCards() {
        countCards = new int[10];                           // Here we will have the number of cards of each type
        cards = Card.sortCards(cards);
        int[] cardsArray = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12};
            /*   for (int i=0;i<cards.size();i++){
            System.out.println("------------------  Cartas IA " + cards.get(i));
            } */
        for (int j = 0; j < cards.size(); j++) {
            for (int a = 0; a < 10; a++) {
                if (cards.get(j).getValue() == cardsArray[a]) {
                    countCards[a] ++;
                }
            }
        }
    }

    public int getBid(int opponentBid, int gameCount){       // opponentBid = -1 if the IA is the first
        HashMap<String, Double[]> map = pujasIA1;
        totalCards = cards.size();
        numCards = cards.size();
        countCards();
        if (gameCount % 500000 == 0){                       // El modulo tendria que ser el numero de rondas quitandole un 0
            ponderacion = ponderacion - 0.1;
            if (ponderacion < 0.0){
                ponderacion = 0.0;
            }
        }

        switch (numCards) {
            case 1:
                map = pujasIA1;
                break;

            case 2:
                map = pujasIA2;
                break;

            case 3:
                map = pujasIA3;
                break;

            case 4:
                map = pujasIA4;
                break;

            case 5:
                map = pujasIA5;
                break;
        }

        if (firstTimeBids[numCards-1] == 1) {                   // The first time, all the bids will have the same probabilities
            firstTimeBids[numCards-1] = 0;
            getStateBids(opponentBid);
            bid = (int) Math.floor(Math.random()*numCards);
        } else {                                                // I have to get the bid with the best reward for this state
            bid = getMaxBids(map, getStateBids(opponentBid), opponentBid);
        }
        System.out.println("\n[AUTO] Bid: " + bid+"\n");
        bid_aux = bid;
        return bid;
    }

    private String getStateBids(int opponentBid) {
        String aux_state = "";
        for (int i = 0; i < countCards.length; i++){            // I search in the array of cards to count the number of cards of each value we have at this moment
            // to make the string of the state
            aux_state = aux_state + "" + countCards[i];
        }
        aux_state = aux_state + "" + opponentBid;
        roundStateBids = aux_state;

        return aux_state;
    }

    private int getMaxBids (HashMap<String, Double[]> map, String state, int opponentBid) {

        double aux = 0.0;
        int index = 0;
        int cont = 0;
        HashMap<String, Double[]> map_aux = map;
        Double[] rewards_aux = map_aux.get(state);

        LinkedList<Double> a = new LinkedList<Double>();
        for (int i = 0; i < rewards_aux.length;i++){
            a.add(rewards_aux[i]);
        }

        do {
            do {
                aux = (Math.random()*1000) / 1000;
                //aux = (long) (aux * 1e3)/1e3;
            } while (aux == 0.0);

            double intervalo = 0;
            for (int i = 0; i < a.size(); i++) {
                intervalo = intervalo + a.get(i);
                if (aux <= intervalo) {
                    index = i;
                    if (index + opponentBid == numCards){
                        a.set(i, 0.0);
                    }
                    break;
                }
            }

            if (cont >= 8){
                Random random = new Random();
                index = random.nextInt(a.size() + 0);
            }
            cont++;

        } while (index + opponentBid == numCards);

        return index;
    }

    public int getMirrorBid(Card otraCarta){
        if(otraCarta.getValue()>7) return 0;    // I loose
        else return 1;                          // I win
    }

    public Card throwCard(int first, int opponentCard){
        HashMap<String, Double[]> map = new HashMap<String, Double[]>();
        numCards = cards.size();
        //System.out.println("------------------  Numero de cartas antes de tirar " + numCards);
        switch (numCards) {
            case 1:
                map = juegoIA1;
                break;

            case 2:
                map = juegoIA2;
                break;

            case 3:
                map = juegoIA3;
                break;

            case 4:
                map = juegoIA4;
                break;

            case 5:
                map = juegoIA5;
                break;
        }

        int cardToThrow = 0;
        int index = 0;
        int num = 0;
        int[] indexArray = { 0, 0, 1, 2, 3, 4, 5, 6, 0, 0, 7, 8, 9};

        if (firstTimeCards[numCards-1] == 1) {
            firstTimeCards[numCards-1] = 0;
            getStateCards(first, opponentCard);
            index = (int) Math.floor(Math.random()*numCards);
            card = cards.get(index);
            cards.remove(index);
        } else {
            cardToThrow = getCardToThrow(map, getStateCards(first, opponentCard));   // It returns the index of the card in the array
            card = getCard(cardToThrow);
        }
        num = card.getValue();
        countCards[indexArray[num]] --;
        System.out.println("\n[AUTO] Auto player throws " + card+"\n");

        return card;
    }

    private String getStateCards(int first, int opponentCard) {     // first = 1 if the IA is the first player to throw card
        String aux_state = "";
        for (int i = 0; i < countCards.length; i++){                // I search in the array of cards to count the number of cards of each value we have at this moment
            aux_state = aux_state + "" + countCards[i];             // to make the string of the state
        }

        if (opponentCard != 13 && opponentCard < 4) opponentCard = 0;
        else if (opponentCard != 13 && opponentCard >= 4 && opponentCard < 7) opponentCard = 1;
        else if (opponentCard != 13 && opponentCard >= 7 && opponentCard < 11) opponentCard = 2;
        else if (opponentCard != 13 && opponentCard <= 12) opponentCard = 3;

        aux_state = aux_state + bid +""+ first + opponentCard;
        //System.out.println(trainString+" aux_state: "+ aux_state +" bid: "+bid+" first: "+first+" opponentCard: "+opponentCard);
        roundState = aux_state;
        return aux_state;
    }

    private int getCardToThrow (HashMap<String, Double[]> map, String state) {
        double aux = 0;
        int index = 0;
        Double[] rewards = map.get(state);
        double intervalo = 0.0;
        double total = 0.0;
        int[] cardsArray = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12};
        //    countcards =   0, 1, 2, 3, 4, 5, 6, 7, 8, 9

        for (int j = 0; j < rewards.length; j++) {
            total = total + rewards[j];
        }

        do {
            aux = (Math.random()*total*1000) / 1000;
            aux = (long) (aux * 1e3)/1e3;
        } while (aux == 0.0);

        for (int i = 0; i < rewards.length; i++){
            intervalo = intervalo + rewards[i];
            if (aux <= intervalo){
                index = i;
                break;
            }
        }

        return cardsArray[index];
    }

    private Card getCard (int num) {
        cards = Card.sortCards(cards);
        Card card;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() == num) {
                card = cards.get(i);
                cards.remove(i);
                return card;
            }
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!No deberia pasar por aqui!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return cards.get(0);            // It will never happen
    }

    public void setResult(int win) {    //If we win the round, win = 1, if we lose, win = 0;
        switch (numCards) {
            case 1:
                break;
            case 2:
                juegoIA2.put(roundState, updateRewardsCards(juegoIA2,win));
                break;

            case 3:
                juegoIA3.put(roundState, updateRewardsCards(juegoIA3,win));
                break;

            case 4:
                juegoIA4.put(roundState, updateRewardsCards(juegoIA4,win));
                break;

            case 5:
                juegoIA5.put(roundState, updateRewardsCards(juegoIA5,win));
                break;
        }
        numCards --;
    }

    private Double[] updateRewardsCards (HashMap<String, Double[]> map, int win){

        Double[] aux_rewards;
        int[] indexArray = { 0, 0, 1, 2, 3, 4, 5, 6, 0, 0, 7, 8, 9};
        //   0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        double sum = 0.0;           //Sum of all rewards that are not from the selected bid
        aux_rewards = map.get(roundState);

        Integer[] valores_baza = {numCards, indexArray[card.getValue()]}; //To update rewards at the end of the round.
        estadosRonda.put(roundState, valores_baza);

        if (win == 1){

            bid = bid-1;

            //if (roundState.charAt(10) == '-' || roundState.charAt(10) == '0') { //If wins but lose a life, rewards decreased

                /* for (int i = 0; i < aux_rewards.length; i++){
                    if (indexArray[card.getValue()] == i){
                        aux_rewards[i] = aux_rewards[i] - aux_rewards[i]*learning_rate_win*ponderacion;
                        sum = sum + aux_rewards[i];
                    } else {
                        sum = sum + aux_rewards[i];
                    }
                }

                for(int i = 0; i < aux_rewards.length; i++){
                    aux_rewards[i] = (aux_rewards[i]/sum);
                } */

            //} else {

                /* for (int i = 0; i < aux_rewards.length; i++){
                    if (indexArray[card.getValue()] == i){
                        aux_rewards[i] = aux_rewards[i] + aux_rewards[i]*learning_rate_win*ponderacion;
                        sum = sum + aux_rewards[i];
                    } else {
                        sum = sum + aux_rewards[i];
                    }
                }

                for(int i = 0; i < aux_rewards.length; i++){
                    aux_rewards[i] = (aux_rewards[i]/sum);
                } */

            //}

        } else if (win == 0) {

            //if (roundState.charAt(10) == '-' || roundState.charAt(10) == '0') {

                /* for (int i = 0; i < aux_rewards.length; i++){
                    if (indexArray[card.getValue()] == i){
                        aux_rewards[i] = aux_rewards[i] + aux_rewards[i]*learning_rate_win*ponderacion;
                        sum = sum + aux_rewards[i];
                    } else {
                        sum = sum + aux_rewards[i];
                    }
                }

                for(int i = 0; i < aux_rewards.length; i++){
                    aux_rewards[i] = (aux_rewards[i]/sum);
                } */

            //} else {

               /*  for (int i = 0; i < aux_rewards.length; i++){
                    if (indexArray[card.getValue()] == i){
                        aux_rewards[i] = aux_rewards[i] - aux_rewards[i]*learning_rate_win*ponderacion;
                        sum = sum + aux_rewards[i];
                    } else {
                        sum = sum + aux_rewards[i];
                    }
                }

                for(int i = 0; i < aux_rewards.length; i++){
                    aux_rewards[i] = (aux_rewards[i]/sum);
                } */

            //}

        }

       /*  for(int i = 0; i < aux_rewards.length; i++){
            aux_rewards[i] = (long) (aux_rewards[i] * 1e3)/1e3;
        } */

         /* for(int i = 0; i < aux_rewards.length; i++){
            System.out.println("Recompensas estado: "+roundState+" : "+aux_rewards[i]);
        }  */

        return aux_rewards;
    }

    public void GlobalRewardCards (int win){    //Updates the rewards at the end of the round taking into account the lifes lost

        LinkedList<String> clean = new LinkedList<String>();
        for (Entry<String, Integer[]> entry : estadosRonda.entrySet()) {
            clean.add(entry.getKey());
            switch (entry.getValue()[0]) {
                case 1:
                    break;
                case 2:
                    juegoIA2.put(entry.getKey(), updateFinalRewardsCards(juegoIA2,win,entry.getKey(),entry.getValue()[1]));
                    break;

                case 3:
                    juegoIA3.put(entry.getKey(), updateFinalRewardsCards(juegoIA3,win,entry.getKey(),entry.getValue()[1]));
                    break;

                case 4:
                    juegoIA4.put(entry.getKey(), updateFinalRewardsCards(juegoIA4,win,entry.getKey(),entry.getValue()[1]));
                    break;

                case 5:
                    juegoIA5.put(entry.getKey(), updateFinalRewardsCards(juegoIA5,win,entry.getKey(),entry.getValue()[1]));
                    break;
            }
        }
        for (String str : clean){
            estadosRonda.remove(str);
        }

    }

    public Double[] updateFinalRewardsCards (HashMap<String, Double[]> map, int win, String state, int cardSelected){

        Double[] aux_rewards;
        double sum = 0.0;           //Sum of all rewards that are not from the selected bid
        aux_rewards = map.get(state);

        if (win == 0){

            for (int i = 0; i < aux_rewards.length; i++){
                if (cardSelected == i){
                    aux_rewards[i] = aux_rewards[i] + aux_rewards[i]*learning_rate_win*ponderacion;
                    sum = sum + aux_rewards[i];
                } else {
                    sum = sum + aux_rewards[i];
                }
            }

            for(int i = 0; i < aux_rewards.length; i++){
                aux_rewards[i] = (aux_rewards[i]/sum);
            }

        } else if (win != 0 && win>=1) {

            for (int i = 0; i < aux_rewards.length; i++){
                if (cardSelected == i){
                    aux_rewards[i] = aux_rewards[i] - aux_rewards[i]*learning_rate_win*ponderacion;
                    sum = sum + aux_rewards[i];
                } else {
                    sum = sum + aux_rewards[i];
                }
            }

            for(int i = 0; i < aux_rewards.length; i++){
                aux_rewards[i] = (aux_rewards[i]/sum);
            }
        }
        for(int i = 0; i < aux_rewards.length; i++){
            aux_rewards[i] = (long) (aux_rewards[i] * 1e3)/1e3;
        }

        return aux_rewards;

    }


    public void setGlobalResult(int win) {    // If we didn't lose lifes, win = 1, if we lose any life, win = 0;

        GlobalRewardCards(win);
        switch (totalCards) {
            case 1:
                pujasIA1.put(roundState, updateRewardsBids(pujasIA1, win));
                break;

            case 2:
                pujasIA2.put(roundState, updateRewardsBids(pujasIA2, win));
                break;

            case 3:
                pujasIA3.put(roundState, updateRewardsBids(pujasIA3, win));
                break;

            case 4:
                pujasIA4.put(roundState, updateRewardsBids(pujasIA4, win));
                break;

            case 5:
                pujasIA5.put(roundState, updateRewardsBids(pujasIA5, win));
                break;
        }

    }

    private Double[] updateRewardsBids (HashMap<String, Double[]> map, int win){

        Double[] aux_rewards;
        double sum = 0.0;           //Sum of all rewards that are not from the selected bid

        aux_rewards = map.get(roundStateBids);

        if (win == 0){

            for (int i = 0; i < aux_rewards.length; i++){
                if (bid_aux == i){
                    aux_rewards[i] = aux_rewards[i] + aux_rewards[i]*learning_rate_win*ponderacion;
                    sum = sum + aux_rewards[i];
                } else {
                    sum = sum + aux_rewards[i];
                }
            }

            for(int i = 0; i < aux_rewards.length; i++){

                aux_rewards[i] = (aux_rewards[i]/sum);
                if (aux_rewards[i].isNaN()){
                    System.out.println("Peta por NaN");
                    System.exit(-1);
                }
            }

        } else if (win != 0 && win>=1) {

            double lifes_penalty = 0.0;
            if (win == 2) {
                lifes_penalty = 0.05;
            } else if (win > 2){
                lifes_penalty = 0.075;
            }

            for (int i = 0; i < aux_rewards.length; i++){
                if (bid_aux == i){
                    aux_rewards[i] = aux_rewards[i] - aux_rewards[i]*(learning_rate_win+lifes_penalty)*ponderacion;
                    sum = sum + aux_rewards[i];
                } else {
                    sum = sum + aux_rewards[i];
                }
            }

            for(int i = 0; i < aux_rewards.length; i++){

                aux_rewards[i] = (aux_rewards[i]/sum);
                if (aux_rewards[i].isNaN()){
                    System.out.println("Peta por NaN "+trainString+"");
                    System.exit(-1);
                }
            }
        }

        return aux_rewards;
    }

    public void writeMaps() {

        LinkedList<HashMap<String,Double[]>> mapsList = new LinkedList<HashMap<String,Double[]>>();
        mapsList.add(pujasIA1);
        mapsList.add(pujasIA2);
        mapsList.add(pujasIA3);
        mapsList.add(pujasIA4);
        mapsList.add(pujasIA5);
        mapsList.add(juegoIA1);
        mapsList.add(juegoIA2);
        mapsList.add(juegoIA3);
        mapsList.add(juegoIA4);
        mapsList.add(juegoIA5);

        try {
            File fileOne=new File("Maps"+trainString);
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(mapsList);
            oos.flush();
            oos.close();
            fos.close();
            System.out.println("Mapas escritos");

        } catch(Exception e) {}
    }

    public void readMaps(String a, Context context) {
        InputStream is = context.getResources().openRawResource(context.getResources().getIdentifier(a, "raw", context.getPackageName()));
        //File toRead=new File(a);
        //FileInputStream fis;

        try {


          //  fis = new FileInputStream(toRead);


            //ObjectInputStream ois=new ObjectInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(is);
            @SuppressWarnings("unchecked")
            LinkedList<HashMap<String,Double[]>> readList =(LinkedList<HashMap<String,Double[]>>)ois.readObject();

            ois.close();
            //fis.close();

            //print All data in MAP
            pujasIA1 = readList.get(0);
            pujasIA2 = readList.get(1);
            pujasIA3 = readList.get(2);
            pujasIA4 = readList.get(3);
            pujasIA5 = readList.get(4);
            juegoIA1 = readList.get(5);
            juegoIA2 = readList.get(6);
            juegoIA3 = readList.get(7);
            juegoIA4 = readList.get(8);
            juegoIA5 = readList.get(9);

            System.out.println("Mapas leidos");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
