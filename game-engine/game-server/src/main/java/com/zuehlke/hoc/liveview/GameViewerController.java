package com.zuehlke.hoc.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameViewerController {

    @Autowired
    private GameHistoryService gameHistoryService;

    @MessageMapping("/register")
    @SendToUser("/topic/poker_register")
    public List<String> register() throws Exception {
        return gameHistoryService.getGameMessages();
    }
}
