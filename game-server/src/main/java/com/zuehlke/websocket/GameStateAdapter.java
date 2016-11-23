package com.zuehlke.websocket;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leo Zulfiu
 *         created on 22.11.2016
 */
@Service
public class GameStateAdapter {
    public State getState(){
        //TODO: call the akka system for the state
        List<String> players = new ArrayList<>();
        players.add("Tom");
        players.add("John");
        State state = new State(300, players);

        return state;
    }

    public static class State {
        int pot;
        List<String> players;

        public State(int pot, List<String> players) {
            this.pot = pot;
            this.players = players;
        }

        public int getPot() {
            return pot;
        }

        public List<String> getPlayers() {
            return players;
        }
    }
}
