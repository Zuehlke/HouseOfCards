package com.zuehlke.hoc.rest;

import java.util.List;

/**
 * Message used to notifier a bot to submit a move.
 *
 * @author Lukas Hofmaier0
 */
public class YourTurnMessage {

    private List<Integer> your_cards;
    private List<PlayerDTO> active_players;
    private long minimum_set;
    private int maximum_set;
    private int pot;

    public long getMinimum_set() {
        return minimum_set;
    }

    public void setMinimum_set(long minimum_set) {
        this.minimum_set = minimum_set;
    }

    public int getMaximum_set() {
        return maximum_set;
    }

    public void setMaximum_set(int maximum_set) {
        this.maximum_set = maximum_set;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public List<PlayerDTO> getActive_players() {
        return active_players;
    }

    public void setActive_players(List<PlayerDTO> active_players) {
        this.active_players = active_players;
    }

    public List<Integer> getYour_cards() {
        return your_cards;
    }

    public void setYour_cards(List<Integer> your_cards) {
        this.your_cards = your_cards;
    }
}
