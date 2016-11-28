package com.zuehlke.hoc.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameViewerController {

    @MessageMapping("/register")
    @SendToUser("/topic/poker_register")
    public SimpleViewerMessage register() throws Exception {
        return new SimpleViewerMessage("REGISTRED");
    }
}
