package com.zuehlke.hoc.liveview;

import com.zuehlke.hoc.actors.ViewNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WebsocketActor {
    private ViewNotifier viewNotifier;

    @Autowired
    public WebsocketActor(ViewNotifier viewNotifier) {
        this.viewNotifier = viewNotifier;
    }

    /* Used to test the communication with the live view */
    @Scheduled(fixedDelay = 5000)
    public void getCurrentStateAndPublish() {
        //viewNotifier.sendGameInfo("Message from notifier.");
    }
}
