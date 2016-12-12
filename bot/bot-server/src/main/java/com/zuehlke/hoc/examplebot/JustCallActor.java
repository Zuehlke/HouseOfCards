package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import com.zuehlke.hoc.rest.server2bot.*;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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
    private UUID uuid;

    public JustCallActor(Credentials credentials){
        this.credentials = credentials;
    }

    public void preStart(){
        Camel camel = CamelExtension.get(getContext().system());

        try {
            camel.context().addRoutes(new CustomRouteBuilder(getSelf(), credentials));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.httpSender = getContext().system().actorOf(Props.create(HttpSenderActor.class));

        RegisterMessage registerMessage = createRegisterMessage(credentials);
        this.httpSender.tell(registerMessage, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if(message instanceof RegistrationInfoMessage){
            RegistrationInfoMessage registrationResponse = (RegistrationInfoMessage) message;
            log.info("received registration response. UUID: {}", registrationResponse.getUUID());
            this.uuid = registrationResponse.getUUID();

        }
        if(message instanceof MatchStartedMessage){
            MatchStartedMessage matchStartedMessage = (MatchStartedMessage) message;
            log.info("received match_started response. Nr of players {} . Initial credit: {}", matchStartedMessage.getMatch_players().size(), matchStartedMessage.getYour_money());
        }
        if(message instanceof RoundStartedMessage){
            RoundStartedMessage roundStartedMessage = (RoundStartedMessage) message;
            log.info("received round_started response. Nr of players {}", roundStartedMessage.getRound_players().size());
        }
        if(message instanceof TurnRequestMessage){
            TurnRequestMessage turnRequestMessage = (TurnRequestMessage) message;
            //log.info("received card: {}, minimal bet is {}", turnRequestMessage.getYour_cards().get(0), turnRequestMessage.getMinimum_set());
            log.info("received turn request message");
            com.zuehlke.hoc.rest.bot2server.SetMessage setMessage = new com.zuehlke.hoc.rest.bot2server.SetMessage();
            setMessage.setAmount(turnRequestMessage.getMinimum_set());
            setMessage.setUuid(this.uuid);
            this.httpSender.tell(setMessage, getSelf());
        }
        if (message instanceof FoldMessage) {
            FoldMessage foldMessage = (FoldMessage) message;
            log.info("received fold_message message. Player {} folded", foldMessage.getPlayerName());
        }
        if (message instanceof com.zuehlke.hoc.rest.server2bot.SetMessage) {
            com.zuehlke.hoc.rest.server2bot.SetMessage setMessage = (com.zuehlke.hoc.rest.server2bot.SetMessage) message;
            log.info("received set_message message. Player {} set {}", setMessage.getPlayerName(), setMessage.getAmount());
        }
        if (message instanceof MatchFinishedMessage) {
            MatchFinishedMessage matchFinishedMessage = (MatchFinishedMessage) message;
            log.info("received match_finished message. Match winners: {}", matchFinishedMessage.getWinners());
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
