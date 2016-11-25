package com.zuehlke.hoc;


import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@RestClientTest(RestBotNotifier.class)
public class BotNotifierTest {


    @Autowired
    private BotNotifier botNotifier;

    @Autowired
    private MockRestServiceServer server;

    @Before
    public void setUp() throws Exception {
        this.server
                .expect(requestTo("http://localhost:2222/update"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
    }

    @Test
    public void sendPlayerInfo() {
        botNotifier.registerBot(new RegisterMessage()
                .setName("Winner Bot")
                .setHostname("localhost")
                .setPort(2222));
        botNotifier.sendPlayerInfo(new Player("Winner Bot"));
        server.verify();
    }
}
