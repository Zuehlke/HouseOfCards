package com.zuehlke.hoc.liveview;

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

    public void publish(String message) {
        template.convertAndSend("/topic/poker_updates", new SimpleViewerMessage(message));
    }



}
