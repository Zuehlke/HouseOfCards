package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.IEngineActor;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
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

    private ActorService actorSystem;

    @Autowired
    public NokerController(ActorService actorSystem) {
        this.actorSystem = actorSystem;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterMessage registerMessage) {
        log.info("Received register call. Register message: {}", registerMessage);
        IEngineActor gameEng = actorSystem.getGameEngine();
        gameEng.registerPlayer(registerMessage);
    }

    @RequestMapping(value = "/fold", method = RequestMethod.POST)
    public void fold(@RequestBody FoldMessage foldMessage) {
        log.info("Received fold message from player with UUID {}", foldMessage.getUuid());

        IEngineActor gameEng = actorSystem.getGameEngine();
        gameEng.fold(foldMessage);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void set(@RequestBody SetMessage setMessage) {
        log.info("Received set message from player with {}. Amount was {}.", setMessage.getUuid(), setMessage.getAmount());
        IEngineActor gameEng = actorSystem.getGameEngine();
        gameEng.setBet(setMessage);
    }
}
