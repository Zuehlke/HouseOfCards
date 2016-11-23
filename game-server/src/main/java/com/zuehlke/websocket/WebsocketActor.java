package com.zuehlke.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WebsocketActor {
    private GameViewerPublishService gameViewerPublishService;
    private GameStateAdapter gameStateAdapter;
    private int counter;

    @Autowired
    public WebsocketActor(GameViewerPublishService gameViewerPublishService, GameStateAdapter gameStateAdapter) {
        this.gameViewerPublishService = gameViewerPublishService;
        this.gameStateAdapter = gameStateAdapter;
        counter = 5;
    }

    @Scheduled(fixedDelay = 3000)
    public void changeSomething() {
        State state = gameStateAdapter.getState();
        state.setPot(state.getPot()+counter);
        gameViewerPublishService.publish(state);
        counter++;
    }
}
