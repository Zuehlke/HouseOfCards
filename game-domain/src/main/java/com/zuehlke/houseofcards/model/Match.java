package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.moves.Move;
import com.zuehlke.houseofcards.PlayerNotifierAdapter;

import java.util.List;


/**
 * This class represents a match of a Noker game.
 * A match consists of two consecutive rounds of betting.
 */
public class Match {

    private List<Player> matchPlayers;
    private Round round;
    private Pot pot;
    private Deck deck;
    private PlayerNotifierAdapter notifier;


    public Match(List<Player> matchPlayers, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.matchPlayers = matchPlayers;
    }

    public void startMatch() {
        this.deck = new Deck();
        pot = new Pot();

        round = new Round(matchPlayers, matchPlayers.get(0), deck, pot, notifier);
        round.startRound();
    }

    public void handleMove(Move move) {
        move.execute(round);
    }

    public List<Player> getMatchPlayers() {
        return matchPlayers;
    }
}
