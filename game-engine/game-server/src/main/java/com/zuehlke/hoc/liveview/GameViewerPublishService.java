package com.zuehlke.hoc.liveview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameViewerPublishService {
    private SimpMessagingTemplate template;

    @Autowired
    public GameViewerPublishService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publish(ViewState viewState) {
        MockViewState mockViewState = new MockViewState(MockViewState.counter+=10);
        template.convertAndSend("/topic/poker_updates", mockViewState);
    }
}


