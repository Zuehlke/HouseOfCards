package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.RegisterMessage;
import org.springframework.web.client.RestTemplate;


public class BotActor {

    private RegisterMessage bot;

    public void pong(String message) {
        RestTemplate template = new RestTemplate();
        //template.postForObject();
    }

}
