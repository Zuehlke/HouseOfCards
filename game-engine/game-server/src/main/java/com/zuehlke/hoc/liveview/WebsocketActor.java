package com.zuehlke.hoc.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WebsocketActor {
    private GameViewerPublishService gameViewerPublishService;

    @Autowired
    public WebsocketActor(GameViewerPublishService gameViewerPublishService) {
        this.gameViewerPublishService = gameViewerPublishService;
    }

    @Scheduled(fixedDelay = 5000)
    public void getCurrentStateAndPublish() {
        gameViewerPublishService.publish("XYZ");
    }
}
