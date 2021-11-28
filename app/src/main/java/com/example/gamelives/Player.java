package com.example.gamelives;

import java.util.*;

public abstract class Player {
    private String name;
    //private int ID;
    protected LinkedList<Card> cards;
    private int lifes;

    public Player(String name){
        this.name=name;
        //ID=ident;
        lifes=5;
        cards = null;
    }

    public String getName(){
        return name;
    }

	/* public int getID(){
		return ID;
	}*/

    public LinkedList<Card> getCards(){
        return cards;
    }

    public int getLifes(){
        return lifes;
    }

    public void setCards(LinkedList<Card> cards){
        this.cards=cards;
        return;
    }

    public void setLifes(int lifes){
        if(lifes < 0) lifes = 0;
        else this.lifes = lifes;
        return;
    }

    public void updateLifes(int lifesLost){
        //Called everytime we finish a round
        //***************************************************USARLO PARA REFUERZO POSITIVO/NEGATIVO IA
        if(lifesLost>this.lifes) lifes = 0;
        else lifes = lifes-lifesLost;
    }

    public boolean isAlive(){
        if(lifes > 0) return true;
        else return false;
    }

    public String stringCards(){
        //RETURNS A STRING WITH THE CARDS SORT
        String resultado;
        resultado="(";
        for(int i=0; i<cards.size();i++){
            resultado=resultado.concat(cards.get(i).toString());
            if(i!=cards.size()-1) resultado=resultado.concat(", ");
        }
        resultado= resultado.concat(")");
        return resultado;
    }
}