package com.zuehlke.hoc.liveview;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameStateAdapterMock implements GameStateAdapter {
    private State state;

    public GameStateAdapterMock() {
        List<String> players = new ArrayList<>();
        players.add("Tom");
        players.add("John");

        Map<String, Integer> playerCashes = new HashMap<>();
        playerCashes.put("Tom", 532);
        playerCashes.put("John", 242);

        List<String> deck = new ArrayList<>();
        deck.add("K");
        deck.add("2");
        deck.add("A");
        deck.add("7");

        int pot = 300;
        this.state = new State(pot, players, playerCashes, deck);
    }

    private State getInnerState(){
        //TODO: call the akka system for the state
        state.setPot(new Random().nextInt(200));
        Map<String, Integer> playerCashes = state.getPlayerCashes();
        playerCashes.put("Tom", new Random().nextInt(100));
        playerCashes.put("John", new Random().nextInt(100));
        state.setPlayerCashes(playerCashes);
        return state;
    }

    @Override
    public ViewState getViewState() {
        return new ViewState(getInnerState());
    }
}
