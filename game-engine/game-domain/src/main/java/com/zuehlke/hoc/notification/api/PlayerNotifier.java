package com.zuehlke.hoc.notification.api;


import java.util.List;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.model.Player;

import java.util.List;

public interface PlayerNotifier {

    void sendCardInfo(String player, int card);


    void playersTurn(String player, long minimumChipsForCall, NokerGame nokerGame);

    /**
     * Is called when a new match is started. E.g. after all players called <code>createPlayer</code> or after a match
     * is finished.
     *
     * @param players a list of all players that participate in this match
     * @param dealer  the player who has the dealer button in the current match
     */
    void matchStarted(List<Player> players, Player dealer);
    void broadcastPlayerRaised(String playerName, long amount);
    void broadcastPlayerCalled(String playerName);
    void broadcastPlayerFolded(String playerName);
    void broadcastNextRound();
    void broadcastNextMatch();
    void broadcastNextRound();

    void playersTurn(String player, long minimumChipsForCall, NokerGame nokerGame);
    void broadcastMatchFinished(List<Player> matchWinners, long pot);
    void broadcastShowdown(List<Player> players);
    void broadcastGameFinished(Player player);

    void broadcastPlayerFolded(Player player);
    void broadcastPlayerSet(Player player, long amount);
}
