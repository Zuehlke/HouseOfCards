package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.zuehlke.hoc.rest.RegisterMessage;

/**
 * Command line applcation to start a bot. The bot will register itself at the competition runner and play the noker game according the rules.
 * @author Lukas Hofmaier
 */
public class BotApplication {

    public static void main(String[] args) {

        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            String uri = args[0];
            String teamname = args[1];
            int port = Integer.parseInt(args[2]);

            RegisterMessage registerMessage = createRegisterMessage(uri, teamname, port);

            ActorSystem system = ActorSystem.create();

            ActorRef player = system.actorOf(Props.create(JustCallActor.class));
            Props httpReceiverProbs = Props.create(HttpSenderActor.class, uri, player);
            ActorRef a = system.actorOf(httpReceiverProbs, "httpclient");
            system.actorOf(Props.create(HttpReceiverActor.class), "httpreceiver");
            a.tell(registerMessage, ActorRef.noSender());

        } else {
            System.out.println("Usage: nokerbot <competitionrunner URI>");
        }
    }

    private static RegisterMessage createRegisterMessage(String uri, String teamname, int port) {
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setName(teamname);
        registerMessage.setHostname(uri);
        registerMessage.setPort(port);
        return registerMessage;
    }
}
