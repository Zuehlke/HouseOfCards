package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.zuehlke.hoc.rest.RegisterMessage;

/**
 * Command line applcation to start a bot. The bot will register itself at the competition runner and play the noker game according the rules.
 * @author Lukas Hofmaier
 */
public class Application {

    public static void main(String[] args) {

        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            String uri = args[0];

            RegisterMessage registerMessage = new RegisterMessage();
            registerMessage.setName("The Necomers");
            registerMessage.setHostname(uri);
            registerMessage.setPort(8081);

            ActorSystem system = ActorSystem.create();
            ActorRef a = system.actorOf(Props.create(HttpClientActor.class), "httpclient");
            a.tell(registerMessage, ActorRef.noSender());

        } else {
            System.out.println("Usage: nokerbot <competitionrunner URI>");
        }
    }
}
