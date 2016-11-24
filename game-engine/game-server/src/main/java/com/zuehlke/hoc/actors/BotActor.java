package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.Bot;
import org.springframework.web.client.RestTemplate;


public class BotActor {

    private Bot bot;

    public void pong(String message) {
        RestTemplate template = new RestTemplate();
        //template.postForObject();
    }

}
