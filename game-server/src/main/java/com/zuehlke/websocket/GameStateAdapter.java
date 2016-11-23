package com.zuehlke.websocket;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
