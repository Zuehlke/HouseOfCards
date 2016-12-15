package com.zuehlke.hoc.examplebot;

import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import com.zuehlke.hoc.rest.bot2server.Bot2ServerMessage;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import com.zuehlke.hoc.rest.server2bot.TurnRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * @author Lukas Hofmaier
 */
public class TobiActor extends JustCallActor {

    private final static Logger log = LoggerFactory.getLogger(TobiActor.class);

    private Stack<Bot2ServerMessage> moveScript = new Stack<>();

    public TobiActor(Credentials credentials){
        super(credentials);

        FoldMessage fold2 = new FoldMessage();
        this.moveScript.push(fold2);

        SetMessage set5 = new SetMessage();
        set5.setAmount(0);
        this.moveScript.push(set5);

        FoldMessage fold1 = new FoldMessage();
        this.moveScript.push(fold1);

        SetMessage raise85 = new SetMessage();
        raise85.setAmount(85);
        this.moveScript.push(raise85);
        log.info("initialise");
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
        log.info("prestart of tobi");
    }

    protected void processTurnRequestMessage(TurnRequestMessage turnRequestMessage) {
        log.info("Tobi received turn request");
        if(this.moveScript.isEmpty()){
            log.info("Something went wrong. Tobi has no scripted moves left. Terminate bot");
        }
        Bot2ServerMessage message = this.moveScript.pop();
        message.setUuid(this.uuid);
        this.httpSender.tell(message, getSelf());
    }
}
