package com.zuehlke.houseofcards;

public interface IEngineActor {
    void registerPlayer(String nickname, String callBackUrl);

    void testSend();
}