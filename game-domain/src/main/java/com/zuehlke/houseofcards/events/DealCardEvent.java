package com.zuehlke.houseofcards.events;

import com.zuehlke.houseofcards.model.Player;

public class DealCardEvent {
    private Player player;
    private int card;

    public DealCardEvent(Player p, int card) {
        this.player = p;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCard() {
        return card;
    }
}