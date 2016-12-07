# Project structure

Here you can find descriptions to all the gradle modules.

* **bot**: sample bot written in java with akka
  * **bot-domain**: contains the actual logic for the bot
  * **bot-server**:

* **game-engine**: backend for running noker
  * **game-domain**: the actual noker game which is protocol agnostic
  * **game-server**: handles communication between bots and the domain
  * **game-server-rest**: contains classes which are used in the bot and game-server
