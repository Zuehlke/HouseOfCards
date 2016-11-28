package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Creator;
import com.zuehlke.hoc.rest.RegisterMessage;

/**
 * Command line applcation to start a bot. The bot will register itself at the competition runner and play the noker
 * game according the rules.
 *
 * @author Lukas Hofmaier
 */
public class BotApplication {

    public static void main(String[] args) {

        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            String uri = args[1];
            String teamname = args[0];
            int port = Integer.parseInt(args[2]);

            ActorSystem system = ActorSystem.create();

            Props playerProbs = Props.create(JustCallActor.class, new Credentials(teamname, uri, port));
            ActorRef player = system.actorOf(playerProbs);

        } else {
            System.out.println("Usage: nokerbot <competitionrunner URI> <teamname> <listening port>");
        }
    }


}
