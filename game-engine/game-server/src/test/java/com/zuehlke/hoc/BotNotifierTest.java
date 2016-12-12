package com.zuehlke.hoc;


import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;



@RunWith(SpringRunner.class)
@RestClientTest(RestBotNotifier.class)
public class BotNotifierTest {

    @Autowired
    private BotNotifier botNotifier;

    @Autowired
    private MockRestServiceServer botMock;

    @MockBean
    private RegistrationService registrationServiceMock;

    private List<String> playerUris;


    @Before
    public void setup() {
        playerUris = loadDummyPlayerUris();
        given(registrationServiceMock.getAllRegisteredUris())
                .willReturn(playerUris);
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


    @Test
    public void broadcastPlayerFolded() {
        expectNotificationBroadcast(playerUris, "player_folded");
        botNotifier.broadcastPlayerFolded("test_player1");
        botMock.verify();
    }

    @Test
    public void broadcastPlayerSet() {
        expectNotificationBroadcast(playerUris, "player_set");
        botNotifier.broadcastPlayerSet("test_player1", 100);
        botMock.verify();
    }

    @Test
    public void broadcastGameFinished() {
        expectNotificationBroadcast(playerUris, "game_finished");
        botNotifier.broadcastGameFinished("test_player1");
        botMock.verify();
    }

    @Test
    public void broadcastMatchFinished() {
        expectNotificationBroadcast(playerUris, "match_finished");
        botNotifier.broadcastMatchFinished(Arrays.asList("player1", "player2"));
        botMock.verify();
    }

    @Test
    public void broadcastShowdown() {
        expectNotificationBroadcast(playerUris, "showdown");
        botNotifier.broadcastShowdown(new ArrayList<>());
        botMock.verify();
    }

    @Test
    public void broadcastRoundStarted() {
        expectNotificationBroadcast(playerUris, "round_started");
        botNotifier.broadcastRoundStarted(new ArrayList<>(), 1, new Player(""));
        botMock.verify();
    }


    /**
     * Checks if all players get a notification at the expected endpoint.
     * @param playerUris URIs of the players
     * @param endpoint target endpoint of notification
     */
    private void expectNotificationBroadcast(List<String> playerUris, String endpoint) {
        playerUris.forEach(playerUri -> {
            botMock.expect(requestTo(String.format("http://%s/%s", playerUri, endpoint)))
                    .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
        });
    }

    private List<String> loadDummyPlayerUris() {
        return Arrays.asList("localhost:1111", "localhost:2222", "localhost:3333");
    }
}
