package com.zuehlke.houseofcards.actors;

import com.zuehlke.houseofcards.HelloService;

public class EngineActor implements IEngineActor {

    private HelloService helloService;

    public EngineActor(HelloService helloService){
        this.helloService = helloService;
    }

    public void registerPlayer(String nickname, String callBackUrl){
        System.out.println(nickname+":"+callBackUrl);
    }

    public void testSend(){
        helloService.hello();
    }
}
