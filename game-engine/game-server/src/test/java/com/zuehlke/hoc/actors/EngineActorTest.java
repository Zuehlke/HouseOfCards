package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.RegistrationService;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class EngineActorTest {

    private BotNotifier botNotifierMock;
    private ViewNotifier viewNotifierMock;
    private IEngineActor engineActor;
    private RegistrationService botRegistrationService;

    @Before
    public void setUp() throws Exception {
        botNotifierMock = mock(BotNotifier.class);
        viewNotifierMock = mock(ViewNotifier.class);
        botRegistrationService = mock(RegistrationService.class);
        engineActor = new EngineActor(botNotifierMock, viewNotifierMock, botRegistrationService);
    }

    @Test
    public void registerPlayer() throws Exception {
        RegisterMessage registerMessage = registerPlayer("hansdampf", "localhost", 8080);

        verify(botRegistrationService).isRegistered(registerMessage.getPlayerName());
    }

    //@Test
    public void fold() {
        FoldMessage foldMessage = new FoldMessage();
        foldMessage.setUuid(UUID.randomUUID());

        when(botNotifierMock.registerBot(any())).thenReturn(true);
        when(botNotifierMock.getPlayerNameByUuid(any())).thenReturn(Optional.of("player1"));

        registerPlayer("player1", "localhost", 8080);
        registerPlayer("player2", "localhost", 8081);

        engineActor.fold(foldMessage);
        verify(botNotifierMock).broadcastPlayerFolded("player1");
    }

    @Test
    public void set() {
        SetMessage setMessage = new SetMessage();
        setMessage.setUuid(UUID.randomUUID());
        setMessage.setAmount(50);

        when(botNotifierMock.registerBot(any())).thenReturn(true);
        when(botNotifierMock.getPlayerNameByUuid(any())).thenReturn(Optional.of("player1"));

        registerPlayer("player1", "localhost", 8080);
        registerPlayer("player2", "localhost", 8081);

        engineActor.setBet(setMessage);
        verify(botNotifierMock).getPlayerNameByUuid(setMessage.getUuid());
        //verify(botNotifierMock).broadcastPlayerSet("player1", 50);
    }


    private RegisterMessage registerPlayer(String playername, String hostname, int port) {
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName(playername);
        registerMessage.setHostname(hostname);
        registerMessage.setPort(port);
        engineActor.registerPlayer(registerMessage);
        return registerMessage;
    }
}