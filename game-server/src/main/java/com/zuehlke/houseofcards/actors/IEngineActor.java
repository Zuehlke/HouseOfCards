package com.zuehlke.houseofcards.actors;

public interface IEngineActor {
    void registerPlayer(String nickname, String callBackUrl);

    void testSend();
}