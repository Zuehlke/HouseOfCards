package com.zuehlke.hoc.notification.api;


import com.zuehlke.hoc.model.Player;

import java.util.List;

public interface PlayerNotifier {

    /**
     * Is called when a players bet is requested. After this call the <code>NokerGame</code> instance expects
     * <code>playerSet</code>.
     *
     * @param player        the player whos bet is requested
     * @param lowerBound    the smallest possible amount for this bet
     * @param upperBound    upper bound for the requested bet
     * @param amountInPot   the current amount of money in the pot
     * @param activePlayers list of player that are still participating in the game and haven't folded in the current
     *                      match.
     */
    void requestBet(Player player, long lowerBound, long upperBound, long amountInPot, List<Player> activePlayers);

    /**
     * Is called when a new match is started. E.g. after all players called <code>createPlayer</code> or after a match
     * is finished.
     *
     * @param players a list of all players that participate in this match
     * @param dealer  the player who has the dealer button in the current match
     */
    void matchStarted(List<Player> players, Player dealer);

    void broadcastNextMatch();
    void broadcastNextRound();

    void broadcastMatchFinished(List<Player> matchWinners, long pot);
    void broadcastShowdown(List<Player> players);
    void broadcastGameFinished(Player player);

    void broadcastPlayerFolded(Player player);
    void broadcastPlayerSet(Player player, long amount);
}
