WebSockets is an advanced technology that makes it possible to open an interactive communication session between the user's browser and a server. WebSockets use TCP for communcation.

In this project, a thin layer on top of the websocket protocol is used called **STOMP** (**S**imple **T**ext-**O**rientated **M**essaging **P**rotocol). This defines a common protocol for every client who wants to communicate with any STOMP message broker.
STOMP with spring uses channels to recieve and broadcast messages to clients. A client has to subscribe to a channel to get messages from the broker.

## Using Websockets in spring
### Backend

1. Add needed dependencies to gradle.build
```
compile('org.springframework:spring-messaging')
compile('org.springframework.boot:spring-boot-starter-websocket')

//JavaScript dependencies
compile('org.webjars:sockjs-client:1.1.1')
compile('org.webjars:stomp-websocket:2.3.3')
```

2. Create socket config
  * Create WebSocketConfig which extends from AbstractWebSocketMessageBrokerConfigurer
  * register Stomp endpoint, define here that this endpoint will use SockJS (`.withSockJS()`)

3. Send messages from broker to clients
  * inject SimpMessagingTemplate
  * send message with `template.convertAndSend("your/channel", object)`

4. Receive messages from clients to broker
  * Create controller class
  * Annotate receiving methods with `@MessageMapping("/your/channel")` annotation

### Frontend

1. Add JS dependencies
```
<script src="/webjars/sockjs-client/1.1.1/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.js"></script>
```

2. Create SockJS and configure Stomp
```
var socket = new SockJS('your-configured-endpoint-in-WebSocketConfig');
stompClient = Stomp.over(socket);
```

3. Subscribe to a channel
```
stompClient.subscribe('/channel/to/subscribe/', callback)
```

4. Send messages to broker
```
stompClient.send("/channel/to/send", headerObj, bodyObj);
```
