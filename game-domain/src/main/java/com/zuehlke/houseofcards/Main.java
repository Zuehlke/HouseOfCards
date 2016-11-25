package com.zuehlke.houseofcards;



public class Main {
    public static void main(String[] args) {

        Table table = new Table(new NokerGame(2));
        State currentState;

        currentState = table.registerPlayer("Tom");
        System.out.println(currentState);

        currentState = table.registerPlayer("Pete");
        System.out.println(currentState);

        currentState = table.handleMove(new FoldMove("Tom"));
        currentState = table.handleMove(new FoldMove("Pete"));


        currentState = table.handleMove(new RaiseMove("Tom", 50));
        System.out.println(currentState);



    }
}
