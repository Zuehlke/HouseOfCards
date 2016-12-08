package com.zuehlke.hoc.examplebot;

import akka.actor.UntypedActor;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.stream.ActorMaterializer;
import com.zuehlke.hoc.rest.RegisterMessage;
import com.zuehlke.hoc.rest.SetMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for sending HTTP message to the competition runner.
 * @author Lukas Hofmaier
 */
class HttpSenderActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(HttpSenderActor.class);

    /**
     * Forwards RegisterMessages to the competition runner.
     *
     * @param message that contains host, port and player name
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        ActorMaterializer materializer = ActorMaterializer.create(getContext().system());
        ObjectMapper objectMapper = new ObjectMapper();
        if (message instanceof RegisterMessage) {
            RegisterMessage registerMessage = (RegisterMessage) message;

            String serializedRegisterMessage = objectMapper.writeValueAsString(registerMessage);
            HttpRequest request = HttpRequest.POST("http://localhost:8080/noker/register").withEntity(ContentTypes.APPLICATION_JSON, serializedRegisterMessage);

            Http.get(getContext().system())
                    .singleRequest(request, materializer);
            log.info("Send register message: Name: {}, HostName: {}:{}", registerMessage.getPlayerName(), registerMessage.getHostname(), registerMessage.getPort());
        }
        if(message instanceof SetMessage){
            SetMessage setMessage = (SetMessage)message;
            String serializedSetMessage = objectMapper.writeValueAsString(setMessage);
            HttpRequest request = HttpRequest.POST("http://localhost:8080/noker/set").withEntity(ContentTypes.APPLICATION_JSON, serializedSetMessage);
            Http.get(getContext().system())
                    .singleRequest(request, materializer);
            log.info("Send set message: Amount: {}, UUID: {}", setMessage.getAmount(), setMessage.getUuid());
        }
    }
}
