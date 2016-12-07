# Work planning

## Pseudo sprint 2 (until 29.11.16)

### bot (luho)
* create a bot to test drive the competition runner (module bot/bot-server)
* Handle requests from bots in competition runner (NokerController)
* Players can register for new game (EngineActor)
* Player are notified wheter registration was successfull
* Logging in RestController
* Broadcast game start event to registered bots

### game server domain (yast, lezu)
* Getting messages, changing state and responding with a new message
* Raise limit
* Notifier events
* Show/hide hand at the end of a match possibility

### REST (aalb)
* Processing first REST calls up to the engine and back with AKKA

## Pseudo sprint 1

### init. project (domu)
* gradle projects (server-domain, bot-domain, bot-tomcat, server-tomcat, reactive-tomcat)
* CI

### viewer (ralo,lezu)
* Hallo Welt (with update)

### game server domain (yast)
* incl. game protocol

### server client communication (aalb)
* Rest-bidirektional

### reactive (akka) (luho)
* Nebenläufigkeit
* Spring / AKKA

### bot domain --> on hold
