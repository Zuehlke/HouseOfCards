package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.FoldMessage;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EngineActorTest {

    private BotNotifier botNotifierMock;
    private ViewNotifier viewNotifierMock;
    private IEngineActor engineActor;


    @Before
    public void setUp() throws Exception {
        botNotifierMock = mock(BotNotifier.class);
        viewNotifierMock = mock(ViewNotifier.class);
        engineActor = new EngineActor(botNotifierMock, viewNotifierMock);
    }

    @Test
    public void registerPlayer() throws Exception {
        RegisterMessage registerMessage = registerPlayer("hansdampf", "localhost", 8080);
        verify(botNotifierMock).registerBot(registerMessage);
    }

    @Test
    public void fold() {
        FoldMessage foldMessage = new FoldMessage();
        foldMessage.setUuid(UUID.randomUUID());

        when(botNotifierMock.registerBot(any())).thenReturn(true);
        when(botNotifierMock.getPlayerNameByUuid(any())).thenReturn(Optional.of("player1"));

        registerPlayer("player1", "localhost", 8080);
        registerPlayer("player2", "localhost", 8081);

        engineActor.fold(foldMessage);
        verify(botNotifierMock).playerFolded("player1");
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