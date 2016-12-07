package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
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
class JustCallActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(JustCallActor.class);

    private ActorRef httpSender;

    private final Credentials credentials;

    public JustCallActor(Credentials credentials){
        this.credentials = credentials;
    }

    public void preStart(){
        Props httpReceiverProps = Props.create(HttpReceiverActor.class, this.credentials, getSelf());

        Camel camel = CamelExtension.get(getContext().system());

        ActorRef httpReceiver = getContext().system().actorOf(httpReceiverProps);

        try {
            camel.context().addRoutes(new CustomRouteBuilder(httpReceiver));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.httpSender = getContext().system().actorOf(Props.create(HttpSenderActor.class));

        RegisterMessage registerMessage = createRegisterMessage(credentials);
        this.httpSender.tell(registerMessage, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if(message instanceof GameEvent){
            GameEvent gameEvent = (GameEvent)message;
            log.info("Received message: {}", gameEvent);
            switch(gameEvent.getEventKind()){
                case START: log.info("Start game received");
                    getContext().stop(getSelf());
                    getContext().system().terminate();
                    break;
                case NAME_ALREADY_TAKEN: log.info(String.format("The user name %s is already taken", credentials.getHostname()));
                    RegisterMessage registerMessageWithAlteredName = createRegisterMessage(credentials);
                    registerMessageWithAlteredName.setPlayerName(String.format("%s_", registerMessageWithAlteredName.getPlayerName()));
                    log.info(String.format("Retry registration with name %s.", registerMessageWithAlteredName.getPlayerName()));
                    this.httpSender.tell(registerMessageWithAlteredName,getSelf());
                    break;
                case RESERVATION_CONFIRMATION:log.info("Registration confirmed. Wait for game to start");
                    break;
                default: log.info("Received unknown RegistrationResponse: %s", gameEvent.getEventKind().toString());
            }
        }
    }

    private static RegisterMessage createRegisterMessage(Credentials credentials) {
        RegisterMessage registerMessage = new RegisterMessage();
        registerMessage.setPlayerName(credentials.getName());
        registerMessage.setHostname(credentials.getHostname());
        registerMessage.setPort(credentials.getPort());
        return registerMessage;
    }
}
