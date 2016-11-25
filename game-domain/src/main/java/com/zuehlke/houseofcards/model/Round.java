package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.Deck;
import com.zuehlke.houseofcards.Player;
import com.zuehlke.houseofcards.PlayerNotifierAdapter;
import com.zuehlke.houseofcards.events.DealCardEvent;

import java.util.List;

public class Round {
    private PlayerNotifierAdapter notifier;
    private List<Player> roundPlayers;
    private Player currentPlayer;
    private Deck deck;

    public Round(List<Player> players, Player currentPlayer, Deck deck, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.roundPlayers = players;
        this.currentPlayer = currentPlayer;
        this.deck = deck;
    }

    public void startRound() {
        dealCard();
        notifier.askPlayerForAction(currentPlayer);
    }

    public void dealCard() {
        roundPlayers.forEach(p -> {
            int card = deck.drawCard();
            p.addCard(card);
            notifier.publishToPlayer(new DealCardEvent(p, card));
        });
    }

    public List<Player> getRoundPlayers() {
        return roundPlayers;
    }
}
