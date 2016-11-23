package com.zuehlke.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WebsocketActor {
    private GameViewerPublishService gameViewerPublishService;
    private GameStateAdapter gameStateAdapter;

    @Autowired
    public WebsocketActor(GameViewerPublishService gameViewerPublishService, GameStateAdapter gameStateAdapter) {
        this.gameViewerPublishService = gameViewerPublishService;
        this.gameStateAdapter = gameStateAdapter;
    }

    @Scheduled(fixedDelay = 2500)
    public void getCurrentStateAndPublish() {
        gameViewerPublishService.publish(gameStateAdapter.getViewState());
    }
}
