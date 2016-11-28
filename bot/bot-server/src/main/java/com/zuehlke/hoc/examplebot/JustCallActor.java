package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.zuehlke.hoc.rest.GameEvent;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reacts on messages from the competition runner and decides the next move. This actor will register itself and
 * afterward it will send the move "call" if it has enough credit. When the game is over it will terminated the
 * application.
 *
 * @author Lukas Hofmaier
 */
public class JustCallActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(JustCallActor.class);

    private ActorRef httpSender;

    private final Credentials credentials;

    public JustCallActor(Credentials credentials){
        this.credentials = credentials;
    }

    public void preStart(){
        Props httpReceiverProbs = Props.create(HttpReceiverActor.class, this.credentials, getSelf());
        getContext().system().actorOf(httpReceiverProbs);

        this.httpSender = getContext().system().actorOf(Props.create(HttpSenderActor.class));

        RegisterMessage registerMessage = createRegisterMessage(credentials);
        this.httpSender.tell(registerMessage, ActorRef.noSender());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof GameEvent){
            GameEvent gameEvent = (GameEvent)message;
            log.info("Received message: {}", gameEvent);

            getContext().stop(getSelf());
            getContext().system().terminate();
        }
    }

    private static RegisterMessage createRegisterMessage(Credentials credentials) {
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setName(credentials.getName());
        registerMessage.setHostname(credentials.getHostname());
        registerMessage.setPort(credentials.getPort());
        return registerMessage;
    }
}
