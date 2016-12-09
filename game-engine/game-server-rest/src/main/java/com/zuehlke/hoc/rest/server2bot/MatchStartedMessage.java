package com.zuehlke.hoc.rest.server2bot;

import com.zuehlke.hoc.rest.PlayerDTO;

import java.util.List;

/**
 * Used to signal the start of a match to the bots.
 *
 * @author Lukas Hofmaier
 */
public class MatchStartedMessage implements Message {

    private List<PlayerDTO> match_players;
    private PlayerDTO dealer;
    private long your_money;

    public List<PlayerDTO> getMatch_players() {
        return match_players;
    }

    public void setMatch_players(List<PlayerDTO> match_players) {
        this.match_players = match_players;
    }

    public PlayerDTO getDealer() {
        return dealer;
    }

    public void setDealer(PlayerDTO dealer) {
        this.dealer = dealer;
    }


    public long getYour_money() {
        return your_money;
    }

    public void setYour_money(long your_money) {
        this.your_money = your_money;
    }

}
