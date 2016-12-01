package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.RegisterMessage;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Lukas Hofmaier
 */
public class EngineActorTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void registerPlayer() throws Exception {

        BotNotifier botNotifierMock = mock(BotNotifier.class);
        ViewNotifier viewNotifier = mock(ViewNotifier.class);

        IEngineActor engineActor = new EngineActor(botNotifierMock, viewNotifier);

        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName("hansdampf");
        registerMessage.setHostname("localhost");
        registerMessage.setPort(8080);

        engineActor.registerPlayer(registerMessage);

        verify(botNotifierMock).registerBot(registerMessage);
        verify(viewNotifier);

    }

}