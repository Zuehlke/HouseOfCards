package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
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
    private final BotNotifier botNotifier;

    private DefaultActorSystem actorSystem;


    @Autowired
    public NokerController(DefaultActorSystem actorSystem, BotNotifier botNotifier) {
        this.actorSystem = actorSystem;
        this.botNotifier = botNotifier;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterMessage input) {
        IEngineActor gameEng = actorSystem.getGameEngine();
        gameEng.registerPlayer(input.getName());
    }
}