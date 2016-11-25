package com.zuehlke.hoc.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameViewerController {
    private GameStateAdapter gameStateAdapter;

    @Autowired
    public GameViewerController(GameStateAdapter gameStateAdapter) {
        this.gameStateAdapter = gameStateAdapter;
    }

    @MessageMapping("/register")
    @SendToUser("/topic/poker_register")
    public MockViewState register() throws Exception {
        return new MockViewState(MockViewState.counter);
        //return gameStateAdapter.getViewState();
    }
}
