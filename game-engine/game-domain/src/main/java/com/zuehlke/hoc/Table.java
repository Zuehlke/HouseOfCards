package com.zuehlke.hoc;


public class Table {

    private Game game;

    public Table(Game game) {
        this.game = game;
    }

    public void registerPlayer(String playerName) {
        game.addPlayer(new Player(playerName));
    }

    public void startGame() {
        if (game.isReady()) {
            game.start();
        }
    }

    public Game getCurrentGame(){
        return game;
    }

    public void handleAction(Action action) {
        game.handleAction(action);
    }
}
