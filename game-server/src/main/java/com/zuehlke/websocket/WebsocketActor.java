package com.zuehlke.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Leo Zulfiu
 *         created on 22.11.2016
 */
@Component
@EnableScheduling
public class WebsocketActor {
    int counter;
    private ViewerPublishService viewerPublishService;

    @Autowired
    public WebsocketActor(ViewerPublishService viewerPublishService) {
        this.viewerPublishService = viewerPublishService;
    }

    @Scheduled(fixedDelay = 3000)
    public void changeSomething() {
        viewerPublishService.publish(counter+"");
        counter++;
    }
}
