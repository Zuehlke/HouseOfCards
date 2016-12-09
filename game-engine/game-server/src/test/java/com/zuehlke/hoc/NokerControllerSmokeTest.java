package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.EngineActor;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Lukas Hofmaier
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NokerControllerSmokeTest {

    EngineActor engineActor = mock(EngineActor.class);
    ActorService actorService = mock(ActorService.class);

    private NokerController nokerController = new NokerController(actorService);

    @Test
    public void register() {
        when(actorService.getGameEngine()).thenReturn(engineActor);
        assertThat(nokerController).isNotNull();
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName("The Hosts");
        registerMessage.setHostname("localhost");
        registerMessage.setPort(8080);
        nokerController.register(registerMessage);
        verify(actorService).getGameEngine();
        verify(engineActor).registerPlayer(registerMessage);
    }
}
