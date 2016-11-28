package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.RegisterMessage;

public interface IEngineActor {

    void registerPlayer(RegisterMessage registerMessage);

}