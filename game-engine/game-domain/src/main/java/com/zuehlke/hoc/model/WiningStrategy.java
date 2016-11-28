package com.zuehlke.hoc.model;

import java.util.List;


/**
 * Calculates the winner of a match.
 */
public class WiningStrategy {

    public static Player winner(List<Player> matchPlayers, Bets bets) {
        return matchPlayers.get(0);
    }
}
