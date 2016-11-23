package com.zuehlke.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Leo Zulfiu
 *         created on 22.11.2016
 */
@Service
public class ViewerPublishService {
    private SimpMessagingTemplate template;

    @Autowired
    public ViewerPublishService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publish(String action) {
        //TODO: fitler and prepare state for broadcasting
        template.convertAndSend("/topic/poker_updates", action);
    }
}
