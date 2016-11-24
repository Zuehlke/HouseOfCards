package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.actors.DefaultActorSystem;
import com.zuehlke.houseofcards.actors.IEngineActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/noker")
public class NokerController {

    private static final Logger log = LoggerFactory.getLogger(NokerController.class.getName());

    private DefaultActorSystem actorSystem;

    public NokerController(@Autowired DefaultActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void register() {
        IEngineActor gameEng = actorSystem.getGameEng();
        gameEng.registerPlayer("one", "localhost:3333");
        gameEng.testSend();

    }
}
