package com.example.gamelives;
public final class Card implements Comparable<Card>{
    private int value; //1, 2, 3, 4, 5, 6, 7, 10, 11, 12
    private char suit; // O, C, E, B

    public Card(String representation){
        //Representation: NS - [N = number, S = suit] (i.e. 3C = 3 'copas')
        char v=representation.charAt(0);
        if (v == 's') value = 10;
        else if (v == 'c') value = 11;
        else if (v == 'r') value = 12;
        else if (isValue(v)) value = Integer.parseInt("" + v);
        else value = -1;

        char suit=representation.charAt(1);
        if(isSuit(suit)) this.suit = suit;
        else suit = 'F';
    }

    public Card(char v, char s){
        if (v == 's') value = 10;
        else if (v == 'c') value = 11;
        else if (v == 'r') value = 12;
        else if (isValue(v)) value = Integer.parseInt("" + v);

        if(isSuit(s)) suit=s;
        else suit = 'F';
    }

    public boolean initialized(){
        if(value != -1 && suit != 'F') return true;
        else return false;
    }

    public int getValue(){
        return value;
    }

    public char getSuit(){
        return suit;
    }

    public static boolean isValue(char c){
        //Check if a char collected is a correct value
        if(c=='1' | c=='2' | c=='3' | c=='4' | c=='5' | c=='6' | c=='7' | c=='s' | c=='c' | c=='r') return true;
        else return false;
    }

    public static boolean isSuit(char suit){
        if(suit=='o' || suit=='c' || suit=='e' || suit=='b') return true;
        else return false;
    }

    //Override compareTo in class Card
    public int compareTo(Card otracarta){
        int suit1, suit2;
        if(this.value > otracarta.value) return 1;
        if(this.value < otracarta.value) return -1;
        if(this.value == otracarta.value){
            suit1 = gradeSuit(this.suit);
            suit2 = gradeSuit(otracarta.suit);
            if(suit1 < suit2) return -1;
            if(suit1 > suit2) return 1;
            if(suit1 == suit2) return 0;
        }
        // If here error
        System.out.println("[ERROR] Error in compareTo (class Card).");
        return 0;
    }

    private int gradeSuit(char s){
        //Grades the suits to use in compareTo (sort card)
        switch (s){
            case 'o': return 1;
            case 'c': return 2;
            case 'e': return 3;
            case 'b': return 4;
            default: return 0;
        }
    }


    //overrride
    public String toString(){
        return ""+ value + suit;
    }

    public boolean equals(Card otherCard){
        if(otherCard.suit == this.suit && otherCard.value == this.value) return true;
        else return false;
    }
}
