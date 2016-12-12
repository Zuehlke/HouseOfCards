# Architecture overview

![](images/architecture_overview_detailed.png)

*NokerController* is the entry point for the communication from the bots to the game server and provides all necessary
REST endpoints. The *EngineActor* handles the incoming messages from the *NokerController* and delegates them to the
*NokerGame*, which updates the game state. Since the application is in real-time and stateful, it is important that
messages are handled in a synchronized way.

Game events caused by updates to the game state are sent back to the bots by the *RestBotNotifier*.
At the same time web live views are notified from the *WebSocketNotifier* about the game events.
Web live views communicate with the game server using WebSockets. A web view registers at the *GameViewerController*
and receives updates from the *GameViewerPublishService*.