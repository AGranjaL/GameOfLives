package com.example.gamelives;

import java.util.Scanner;

public class ManualPlayer extends Player{

    public ManualPlayer(String name){
        super(name);
    }

    public Card throwCard(Scanner sc, Card otraCarta){
        System.out.println("[MANUAL] Your cards are: " + this.stringCards());
        //if(otraCarta!=null) System.out.println("[MANUAL] Auto player threw " + otraCarta);
        while(true){
            System.out.println("[MANUAL] Write in capitals the card you want to throw: ");
            String card = sc.nextLine();
            card = card.trim();
            System.out.println(card);
            if(card.length()==2){
                Card c = new Card(card);
                if(c.initialized()){
                    for(int i=0;i<cards.size();i++){
                        if(cards.get(i).toString().equals(c.toString())){
                            cards.remove(cards.get(i));
                            return c;
                        }
                    }
                    System.out.println("[MANUAL] You haven't the card selected.");
                }else System.out.println("[MANUAL] Wrong selection.");
            }
        }
    }

    public int getMirrorBid(Scanner sc, Card otraCarta){
        System.out.println("[MANUAL] Other player card is: " +  otraCarta);
        do{
            System.out.println("[MANUAL] Do you think you win? (y/n)");
            String read = sc.nextLine();
            read = read.trim();
            if(read.charAt(0)=='y') return 1;
            else if(read.charAt(0)=='n') return 0;
        }while(true);
    }

    public int getBid(Scanner sc, int otraBid){
        System.out.println("[MANUAL] Your cards are: " + this.stringCards());
        System.out.print("[MANUAL] Introduce your bid: ");
        int bid = sc.nextInt();
        while((bid+otraBid)==cards.size() || bid>cards.size()){
            System.out.println("[MANUAL] Amount of bids cannot be equal to the number of cards");
            bid = sc.nextInt();
        }
        System.out.println("[MANUAL] Bid: " + bid);
        return bid;
    }
}