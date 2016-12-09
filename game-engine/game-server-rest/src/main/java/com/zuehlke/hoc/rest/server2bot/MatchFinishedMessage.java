package com.zuehlke.hoc.rest.server2bot;


import com.zuehlke.hoc.rest.PlayerDTO;

import java.util.ArrayList;
import java.util.List;



public class MatchFinishedMessage implements Message {
    private List<String> winners;

    public MatchFinishedMessage() {
        winners = new ArrayList<>();
    }

    public MatchFinishedMessage(List<String> winners) {
        this.winners = winners;
    }

    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }
}
