package com.zuehlke.hoc.examplebot;

import com.zuehlke.hoc.rest.bot2server.Bot2ServerMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import com.zuehlke.hoc.rest.server2bot.TurnRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Lukas Hofmaier
 */
public class RikiBotActor extends JustCallActor {

    private final static Logger log = LoggerFactory.getLogger(RikiBotActor.class);

    private Stack<Bot2ServerMessage> moveScript = new Stack<>();

    public RikiBotActor(Credentials credentials) {
        super(credentials);

        SetMessage call = new SetMessage();
        call.setAmount(0);
        this.moveScript.push(call);

        SetMessage raise5 = new SetMessage();
        raise5.setAmount(5);
        this.moveScript.push(raise5);

        SetMessage call85 = new SetMessage();
        call85.setAmount(85);
        this.moveScript.push(call85);
    }

    protected void processTurnRequestMessage(TurnRequestMessage turnRequestMessage) {
        log.info("Riki received turn request");
        if (this.moveScript.isEmpty()) {
            log.info("Riki has no scripted moves left. Did you start riki before tobi?. Terminating bot");
            getContext().system().terminate();
        } else {
            Bot2ServerMessage message = this.moveScript.pop();
            message.setUuid(this.uuid);
            this.httpSender.tell(message, getSelf());
        }
    }
}
