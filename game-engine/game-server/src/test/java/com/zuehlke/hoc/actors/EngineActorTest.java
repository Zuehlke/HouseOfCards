package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.RegistrationService;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static java.util.Optional.of;
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
    public void buildRegisterMessage() throws Exception {
        RegisterMessage registerMessage = buildRegisterMessage("hansdampf", "localhost", 8080);
        engineActor.registerPlayer(registerMessage);
        verify(botRegistrationService).isRegistered(registerMessage.getPlayerName());
    }

    //@Test
    public void fold() {
        FoldMessage foldMessage = new FoldMessage();
        foldMessage.setUuid(UUID.randomUUID());

        //when(botNotifierMock.registerBot(any())).thenReturn(true);
        // when(botNotifierMock.getPlayerNameByUuid(any())).thenReturn(Optional.of("player1"));

        RegisterMessage registerMessage1 = buildRegisterMessage("player1", "localhost", 8080);
        RegisterMessage registerMessage2 = buildRegisterMessage("player2", "localhost", 8081);

        engineActor.registerPlayer(registerMessage1);
        engineActor.registerPlayer(registerMessage2);

        engineActor.fold(foldMessage);
        verify(botNotifierMock).broadcastPlayerFolded("player1");
    }

    @Test
    public void set() {
        SetMessage setMessage = new SetMessage();
        UUID uuid = UUID.randomUUID();
        setMessage.setUuid(uuid);
        setMessage.setAmount(50);

        //when(botNotifierMock.registerBot(any())).thenReturn(true);
        //when(botNotifierMock.getPlayerNameByUuid(any())).thenReturn(Optional.of("player1"));

        RegisterMessage registerMessage = buildRegisterMessage("player1", "localhost", 8080);
        RegisterMessage registerMessage1 = buildRegisterMessage("player2", "localhost", 8081);

        Player player = new Player("player1");

        when(botRegistrationService.getPlayerByUuid(setMessage.getUuid())).thenReturn(of(player));

        engineActor.registerPlayer(registerMessage);
        engineActor.registerPlayer(registerMessage1);
        engineActor.setBet(setMessage);
        verify(botRegistrationService).getPlayerByUuid(setMessage.getUuid());
        //verify(botNotifierMock).broadcastPlayerSet("player1", 50);
    }

    private RegisterMessage buildRegisterMessage(String playername, String hostname, int port) {
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName(playername);
        registerMessage.setHostname(hostname);
        registerMessage.setPort(port);
        return registerMessage;
    }
}