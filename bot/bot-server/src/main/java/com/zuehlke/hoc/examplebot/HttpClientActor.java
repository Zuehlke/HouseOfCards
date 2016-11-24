package com.zuehlke.hoc.examplebot;

import akka.actor.UntypedActor;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.stream.ActorMaterializer;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Responsible for sending HTTP message to the competition runner.
 * @author Lukas Hofmaier
 */
public class HttpClientActor extends UntypedActor {

    /**
     * Forwards RegisterMessages to the competitionrunner.
     *
     * @param message that contains host, port and player name
     * @throws Throwable
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof RegisterMessage) {
            RegisterMessage registerMessage = (RegisterMessage) message;
            ActorMaterializer materializer = ActorMaterializer.create(getContext().system());

            ObjectMapper objectMapper = new ObjectMapper();
            String string = objectMapper.writeValueAsString(registerMessage);
            HttpRequest request = HttpRequest.POST("http://localhost:8080/noker/register").withEntity(ContentTypes.APPLICATION_JSON, string);

            Http.get(getContext().system())
                    .singleRequest(request, materializer);
        }
    }
}
