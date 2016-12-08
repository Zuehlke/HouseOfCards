package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.RegisterMessage;
import com.zuehlke.hoc.rest.SetMessage;

public interface IEngineActor {

    void registerPlayer(RegisterMessage registerMessage);

    void setBet(SetMessage setMessage);

}