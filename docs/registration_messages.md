# Registration messages

![](images/msgs_in_registration_phase.png)

## Game-server endpoints
### /register
Content:
```
{
    hostname: "...",
    port: ...,
    playerName: "..."
}
```

Preconditions:

1. Registration is open.

Use-cases:

1. If playerName is not set in register then a new name is generated and send back (via **/registration_info**) to the bot.
1. If playerName is a duplication then a new name is generated/altered and send back (via **/registration_info**) to the bot.
1. If hostname or port is missing then this message will be ignored. (Null or empty doesnt matter)
1. If hostname or especially port (0-65'535) is not correct, then the registration will be ignored.
1. If hostname seems correct (no validation possible) and port is correct, then the player will be registered.
1. If registration is closed, then 3 and 4 will be still checked and in a valid case a message to the bot-endpoint **/error** will be send.

## Bot endpoints

### /registration_info
Content:
```
{
    infoMessage: "Registration confirmed",
    playerName: "...",
    uuid: "123e4567-e89b-..."
}
```

The uuid is used for identification of the players during game phase.

### /error
Content:
```
{
    errorMessage: "Registration closed"
}
```
