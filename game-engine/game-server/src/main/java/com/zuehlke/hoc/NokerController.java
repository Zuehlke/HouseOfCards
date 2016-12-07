package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.DefaultActorSystem;
import com.zuehlke.hoc.actors.IEngineActor;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noker")
public class NokerController {

    private static final Logger log = LoggerFactory.getLogger(NokerController.class.getName());

    private DefaultActorSystem actorSystem;

    @Autowired
    public NokerController(DefaultActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterMessage registerMessage) {
        log.info("Received register call");
        log.info("Register message: {}", registerMessage);

        IEngineActor gameEng = actorSystem.getGameEngine();
        gameEng.registerPlayer(registerMessage);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void set(@RequestBody String registerMessage) {
        log.info("Received set call");
    }
}
