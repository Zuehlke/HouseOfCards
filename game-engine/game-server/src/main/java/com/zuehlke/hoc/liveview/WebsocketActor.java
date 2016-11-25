package com.zuehlke.hoc.liveview;

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

    @Scheduled(fixedDelay = 5000)
    public void getCurrentStateAndPublish() {
        ViewState viewState = gameStateAdapter.getViewState();
        gameViewerPublishService.publish(viewState);
    }
}
