package com.zuehlke.hoc.examplebot;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;

/**
 * Command line application to start a bot. The bot will register itself at the competition runner and play the noker
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
            String strategy = args[3];

            ActorSystem system = ActorSystem.create();

            Props playerProps;
            switch (strategy) {
                case "tobi":
                    playerProps = Props.create(TobiActor.class, new Credentials(teamname, uri, port));
                    system.actorOf(playerProps);
                    break;
                case "riki":
                    playerProps = Props.create(RikiBotActor.class, new Credentials(teamname, uri, port));
                    system.actorOf(playerProps);
                    break;
                default:
                    playerProps = Props.create(JustCallActor.class, new Credentials(teamname, uri, port));
                    system.actorOf(playerProps);
            }

        } else {
            System.out.println("Usage: nokerbot <competitionrunner URI> <teamname> <listening port> riki|tobi");
        }
    }


}
