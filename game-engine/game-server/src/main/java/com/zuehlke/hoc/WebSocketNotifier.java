package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.liveview.GameHistoryService;
import com.zuehlke.hoc.liveview.GameViewerPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotifier implements ViewNotifier {

    private final GameViewerPublishService publisher;
    private GameHistoryService gameHistoryService;
    private int messageCount = 0;

    @Autowired
    public WebSocketNotifier(GameViewerPublishService publisher, GameHistoryService gameHistoryService) {
        this.publisher = publisher;
        this.gameHistoryService = gameHistoryService;
    }

    @Override
    public void sendGameInfo(String info) {
        info = info + " - id: " + messageCount++;
        gameHistoryService.addMessage(info);
        publisher.publish(info);
    }
}
