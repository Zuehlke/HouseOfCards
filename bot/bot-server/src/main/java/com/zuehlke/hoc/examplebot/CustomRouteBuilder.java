package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.camel.internal.component.CamelPath;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukas Hofmaier
 */
public class CustomRouteBuilder extends RouteBuilder{

    private final static Logger log = LoggerFactory.getLogger(CustomRouteBuilder.class);

    private String httpReceiverUri;
    private ActorRef httpReceiverActorRef;
    private int listeningPort;

    public CustomRouteBuilder(ActorRef httpReceiver, Credentials credentials){
        this.httpReceiverUri = CamelPath.toUri(httpReceiver);
        this.httpReceiverActorRef = httpReceiver;
        this.listeningPort = credentials.getPort();
    }

    public void configure() throws Exception{
        //from("jetty:http://localhost:8081/register_info").to(this.httpReceiverUri);

        rest("/register_info").post().to("direct:registerinfo");
        rest("/match_started").post().to("direct:matchstarted");
        rest("/round_started").post().to("direct:roundstarted");
        rest("/yourturn").post().to("direct:yourturn");

        restConfiguration().component("jetty").port(this.listeningPort);

        from("direct:registerinfo").process(new RegisterInfoProcessor(this.httpReceiverActorRef)).transform().constant("aye, captain");
        from("direct:matchstarted").process(new MatchStartedProcessor(this.httpReceiverActorRef)).transform().constant("");
        from("direct:roundstarted").process(new RoundStartedProcessor(this.httpReceiverActorRef)).transform().constant("");
        from("direct:yourturn").process(new YourTurnProcessor(this.httpReceiverActorRef)).transform().constant("");

    }
}
