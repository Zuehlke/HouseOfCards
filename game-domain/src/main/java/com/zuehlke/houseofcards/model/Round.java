package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.PlayerNotifierAdapter;
import com.zuehlke.houseofcards.events.DealCardEvent;

import java.util.List;


/**
 * This class represents a round of the Noker game.
 * A Noker game has two rounds. In each round, the players
 * are given a card from the deck.
 */
public class Round {
    private PlayerNotifierAdapter notifier;
    private List<Player> roundPlayers;
    private Player currentPlayer;
    private Deck deck;
    private Pot pot;

    private long currentCall;
    private long raiseLimit;    // TODO: raiseLimit = chipsstack of poorest player

    public Round(List<Player> players, Player currentPlayer, Deck deck, Pot pot, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.roundPlayers = players;
        this.currentPlayer = currentPlayer;
        this.deck = deck;
        this.pot = pot;
    }

    public void startRound() {
        dealCard();
        currentCall = 0;
        notifier.askPlayerForAction(currentPlayer);
    }

    public void dealCard() {
        roundPlayers.forEach(p -> {
            int card = deck.drawCard();
            p.addCard(card);
            notifier.publishToPlayer(new DealCardEvent(p, card));
        });
    }

    public long getCurrentCall() {
        return currentCall;
    }

    public List<Player> getRoundPlayers() {
        return roundPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void increasePot(long amountOfChips) {
        pot.increase(amountOfChips);
    }

    public void decreasePot(long amountOfChips) {
        pot.decrease(amountOfChips);
    }

    public long getRaiseLimit() {
        return raiseLimit;
    }
}
