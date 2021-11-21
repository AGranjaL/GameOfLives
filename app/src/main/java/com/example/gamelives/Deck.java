package com.example.gamelives;

import java.util.*;

public class Deck {

    private LinkedList<Card> cards = new LinkedList<Card>();

    public Deck(){
        initDeck();
        Collections.sort(cards);
    }

    //Create the deck
    private void initDeck(){
        char[] values= {'1', '2', '3', '4', '5', '6', '7', 's', 'c', 'r'}; //Conjunto de den
        char[] suits= {'b', 'c', 'e', 'o'} ;//Conjunto de palos
        for(int i=0; i<values.length;i++){
            for(int j=0; j<suits.length;j++){
                Card c= new Card (values[i], suits[j]);
                cards.add(c);
            }
        }
    }

    public void sort(){
        Collections.sort(cards);
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public LinkedList<Card> getNcards(int N){
        Random rand =new Random(System.currentTimeMillis());
        LinkedList<Card> nCards = new LinkedList<Card>();
        Card c;
        if(N>cards.size()){
            System.out.println("[ERROR] Not enought cards left in the deck.");
            return null;
        }else if(N<0) System.out.println("[ERROR] Cannot get a negative number of cards.");
        else{
            for (int i=0; i<N; i++){
                c= cards.remove(rand.nextInt(cards.size()));
                nCards.add(c);
            }
        }
        return nCards;
    }


    public void reset(){
        while(cards.size()!= 0){	//Remove remaining cards
            cards.removeFirst();
        }
        initDeck();
        Collections.sort(cards);
    }
}
