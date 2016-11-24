package com.zuehlke.houseofcards.examplebot;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.stream.ActorMaterializer;

/**
 * @author Lukas Hofmaier
 */
public class Application {

    public static void main(String[] args) {

        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            String uri = args[0];

            ActorSystem system = ActorSystem.create();
            ActorMaterializer materializer = ActorMaterializer.create(system);

            Http.get(system)
                    .singleRequest(HttpRequest.create(uri), materializer);
        } else {
            //TODO: print a log message if no args provided
        }
    }
}
