package com.zuehlke.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ViewerService {
    private GameStateAdapter gameStateAdapter;

    @Autowired
    public ViewerService(GameStateAdapter gameStateAdapter) {
        this.gameStateAdapter = gameStateAdapter;
    }

    @MessageMapping("/register")
    @SendToUser("/topic/poker_register")
    public GameStateAdapter.State register() throws Exception {
        return gameStateAdapter.getState();
    }
}
