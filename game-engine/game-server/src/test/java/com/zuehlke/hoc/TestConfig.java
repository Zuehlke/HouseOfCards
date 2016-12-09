package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.IEngineActor;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lukas Hofmaier
 */
@Configuration
class TestConfig {

    @Bean
    ActorService getDefaultActorSystem() {
        System.out.println("loaded mock actor server");
        return () -> {
            System.out.println("getGameEngine");
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
        };
    }
}
