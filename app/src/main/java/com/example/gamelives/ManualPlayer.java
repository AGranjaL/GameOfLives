package com.example.gamelives;

import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.view.View;

import java.util.LinkedList;
import java.util.Scanner;

import java.util.Scanner;

public class ManualPlayer extends Player{
    public ManualPlayer(String name){

        super(name);
    }

    public Card throwCard(Card manualCard){
        System.out.println("[MANUAL] Your cards are: " + this.stringCards());
        //if(otraCarta!=null) System.out.println("[MANUAL] Auto player threw " + otraCarta);
        /*while(true){
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
        }*/
        cards.remove(manualCard);
        return manualCard;

    }

    public int getMirrorBid(Card otraCarta){
        System.out.println("[MANUAL] Other player card is: " +  otraCarta);
        /*do{
            System.out.println("[MANUAL] Do you think you win? (y/n)");
            String read = sc.nextLine();
            read = read.trim();
            if(read.charAt(0)=='y') return 1;
            else if(read.charAt(0)=='n') return 0;
        }while(true);*/
        return 0;
    }

    public int getBid(Button button_ok, NumberPicker numberPicker, int otraBid){
        System.out.println("[MANUAL] Your cards are: " + this.stringCards());
        System.out.print("[MANUAL] Introduce your bid: ");
        //int bid = sc.nextInt();
        int bid = numberPicker.getValue();
        return bid;
    }
}