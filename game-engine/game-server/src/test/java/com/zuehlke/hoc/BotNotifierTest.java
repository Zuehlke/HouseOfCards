package com.zuehlke.hoc;


import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RunWith(SpringRunner.class)
@RestClientTest(RestBotNotifier.class)
public class BotNotifierTest {

    @Autowired
    private BotNotifier botNotifier;

    @MockBean
    private RegistrationService registrationServiceMock;

    @Autowired
    private MockRestServiceServer botMock;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void sendRegistrationConfirmation() {
        String testPlayerName = "test_player";
        String testPlayerUri = "localhost:2222";
        UUID testPlayerUuid = UUID.randomUUID();

        given(registrationServiceMock.getUriByPlayerName(testPlayerName))
                .willReturn(Optional.of(testPlayerUri));

        this.botMock.expect(requestTo(String.format("http://%s/register_info", testPlayerUri)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        RegistrationInfoMessage registrationInfoMessage = new RegistrationInfoMessage();
        registrationInfoMessage.setPlayerName(testPlayerName);
        registrationInfoMessage.setUUID(testPlayerUuid);
        registrationInfoMessage.setInfoMessage(RegistrationInfoMessage.Result.CONFIRMATION);

        botNotifier.sendRegistrationInfo(registrationInfoMessage);
        botMock.verify();
    }
}
