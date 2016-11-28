package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotifier implements ViewNotifier {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketNotifier(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendGameInfo() {

    }

    @Override
    public void onRegisterPlayer(Player player) {
        messagingTemplate.convertAndSend("/topic/player", player);
    }
}
