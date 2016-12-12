package com.zuehlke.hoc.examplebot;

import com.zuehlke.hoc.rest.bot2server.Bot2ServerMessage;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
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
    }

    protected void processTurnRequestMessage(TurnRequestMessage turnRequestMessage) {
        log.info("Tobi received turn request");
        Bot2ServerMessage message = this.moveScript.pop();
        message.setUuid(this.uuid);
        this.httpSender.tell(message, getSelf());
    }
}
