package com.zuehlke.hoc.rest.server2bot;

import com.zuehlke.hoc.rest.PlayerDTO;

import java.util.List;

/**
 * Message used to notifier a bot to submit a move.
 *
 * @author Lukas Hofmaier0
 */
public class YourTurnMessage implements Message {

    private List<Integer> your_cards;
    private List<PlayerDTO> active_players;
    private long minimum_set;
    private long maximum_set;
    private long pot;

    public long getMinimum_set() {
        return minimum_set;
    }

    public void setMinimum_set(long minimum_set) {
        this.minimum_set = minimum_set;
    }

    public long getMaximum_set() {
        return maximum_set;
    }

    public void setMaximum_set(long maximum_set) {
        this.maximum_set = maximum_set;
    }

    public long getPot() {
        return pot;
    }

    public void setPot(long pot) {
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
