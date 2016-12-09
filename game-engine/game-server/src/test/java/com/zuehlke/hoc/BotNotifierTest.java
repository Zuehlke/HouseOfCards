package com.zuehlke.hoc;


import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@RestClientTest(RestBotNotifier.class)
public class BotNotifierTest {

    @Autowired
    private BotNotifier botNotifier;

    @Autowired
    private MockRestServiceServer server;

    @Before
    public void setUp() throws Exception {

    }


    //@Test
    public void registerBot() {
        this.server
                .expect(requestTo("http://localhost:2222/register_info"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
        botNotifier.registerBot(new RegisterMessage()
                .setPlayerName("Winner Bot")
                .setHostname("localhost")
                .setPort(2222));
        server.verify();
    }
}
