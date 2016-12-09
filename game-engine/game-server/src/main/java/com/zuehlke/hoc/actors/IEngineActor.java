package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;

public interface IEngineActor {

    void registerPlayer(RegisterMessage registerMessage);

    void setBet(SetMessage setMessage);

    void fold(FoldMessage foldMessage);
}