package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.Deck;
import com.zuehlke.houseofcards.Player;
import com.zuehlke.houseofcards.PlayerNotifierAdapter;

import java.util.List;

public class Match {
    private Round round;
    private List<Player> matchPlayers;
    private long pot;
    private Deck deck;
    private PlayerNotifierAdapter notifier;

    public Match(List<Player> matchPlayers, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.matchPlayers = matchPlayers;
    }

    public void startMatch() {
        this.deck = new Deck();
        pot = 0;

        round = new Round(matchPlayers, matchPlayers.get(0), deck, notifier);
        round.startRound();
    }

    public void handleMove() {

    }

    public List<Player> getMatchPlayers() {
        return matchPlayers;
    }
}
