package com.example.gamelives;

import java.util.Random;

public class AutoPlayer extends Player{

    public AutoPlayer(String name){
        super(name);
    }

    public int getBid(int otraBid){
        //RANDOM BID
        Random r = new Random(System.currentTimeMillis());
        int bid = r.nextInt(cards.size());
        while((bid+otraBid)==cards.size()){
            bid = r.nextInt(cards.size());
        }
        System.out.println("[AUTO] Bid: " + bid);
        return bid;
    }

    public int getMirrorBid(Card otraCarta){
        if(otraCarta.getValue()>7) return 0; //I loose
        else return 1; //I win
    }

    public Card throwCard(){
        //THROWS A RANDOM CARD
        Random r = new Random(System.currentTimeMillis());
        int index = r.nextInt(cards.size());
        Card card = cards.get(index);
        cards.remove(index);
        System.out.println("[AUTO] Auto player throws " + card);
        return card;
    }

}
