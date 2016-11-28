package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.liveview.GameViewerPublishService;
import com.zuehlke.hoc.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotifier implements ViewNotifier {

    private final GameViewerPublishService publisher;

    @Autowired
    public WebSocketNotifier(GameViewerPublishService publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendGameInfo(String info) {
        publisher.publish(info);
    }
}
