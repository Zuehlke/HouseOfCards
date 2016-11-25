package com.zuehlke.houseofcards;



public class Main {
    public static void main(String[] args) {

        NokerGame game = new NokerGame(2, new PlayerNotifier() {
            @Override
            public void publishStart(StartInfo startInfo) {
                System.out.println(startInfo);
            }

            @Override
            public void publishGameEvent(GameEvent event) {
                System.out.println(event);
            }

            @Override
            public void askPlayerFoAction(PlayerAction action) {
                System.out.println(action);
            }
        });
        State currentState;

        game.createPlayer("Tom");
        game.createPlayer("Pete");





        currentState = game.handleMove(new FoldMove("Tom"));
        currentState = game.handleMove(new FoldMove("Pete"));


        currentState = game.handleMove(new RaiseMove("Tom", 50));
        System.out.println(currentState);



    }
}
