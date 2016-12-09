package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.IEngineActor;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukas Hofmaier
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ControllerUnitTest {

    private ActorService actorService = new ActorService() {
        @Override
        public IEngineActor getGameEngine() {
            return new IEngineActor() {
                @Override
                public void registerPlayer(RegisterMessage registerMessage) {

                }

                @Override
                public void setBet(SetMessage setMessage) {

                }

                @Override
                public void fold(FoldMessage foldMessage) {

                }
            };
        }
    };

    private NokerController nokerController = new NokerController(actorService);

    @Test
    public void register() {
        assertThat(nokerController).isNotNull();
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName("The Hosts");
        registerMessage.setHostname("localhost");
        registerMessage.setPort(8080);
        nokerController.register(registerMessage);

    }
}
