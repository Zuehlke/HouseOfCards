package com.zuehlke.houseofcards.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameViewerPublishService {
    private SimpMessagingTemplate template;

    @Autowired
    public GameViewerPublishService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publish(ViewState viewState) {
        template.convertAndSend("/topic/poker_updates", viewState);
    }
}
