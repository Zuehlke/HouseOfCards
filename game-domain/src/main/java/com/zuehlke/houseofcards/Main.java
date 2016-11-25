package com.zuehlke.houseofcards;



public class Main {
    public static void main(String[] args) {

        NokerGame game = new NokerGame(2, new PlayerNotifier() {
            @Override
            public void publishStart(StartInfo startInfo) {
                System.out.println(startInfo);
            }

            @Override
            public void publishToPlayer(GameEvent event) {
                System.out.println(event);
            }

            @Override
            public void broadcastGameEvent(GameEvent event) {
                System.out.println(event);
            }

            @Override
            public void askPlayerForAction(PlayerAction action) {
                System.out.println(action);
            }
        });
        game.createPlayer("Tom");
        game.createPlayer("Pete");

        game.handleMove(new FoldMove("Tom"));
        game.handleMove(new FoldMove("Pete"));


        game.handleMove(new RaiseMove("Tom", 50));



    }
}
