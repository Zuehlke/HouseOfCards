package com.zuehlke.hoc.rest.server2bot;

import com.zuehlke.hoc.rest.PlayerDTO;

import java.util.List;

/**
 * @author Lukas Hofmaier
 */
public class RoundStartedMessage implements Message {

    private int round_number;
    private List<PlayerDTO> round_players;
    private PlayerDTO round_dealier;

    public int getRound_number() {
        return round_number;
    }

    public void setRound_number(int round_number) {
        this.round_number = round_number;
    }

    public List<PlayerDTO> getRound_players() {
        return round_players;
    }

    public void setRound_players(List<PlayerDTO> round_players) {
        this.round_players = round_players;
    }

    public PlayerDTO getRound_dealier() {
        return round_dealier;
    }

    public void setRound_dealier(PlayerDTO round_dealier) {
        this.round_dealier = round_dealier;
    }
}
