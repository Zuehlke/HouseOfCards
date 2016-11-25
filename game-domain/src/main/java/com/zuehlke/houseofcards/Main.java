package com.zuehlke.houseofcards;



public class Main {
    public static void main(String[] args) {

        NokerGame game = new NokerGame(2, new PlayerNotifier() {

            @Override
            public void publishToPlayer(GameEvent event) {
                System.out.println(event);
            }

            @Override
            public void broadcast(GameEvent event) {
                System.out.println(event);
            }
        });
        game.createPlayer("Tom");
        game.createPlayer("Pete");

        game.handleMove(new CallMove());
        game.handleMove(new FoldMove("Pete"));


        game.handleMove(new RaiseMove("Tom", 50));



    }
}
