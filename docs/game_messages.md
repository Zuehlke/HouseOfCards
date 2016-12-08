# Game messages

![](images/msgs_in_game_phase.png)

## Game-server endpoints

Preconditions:

1. Registration is closed.
2. Game has started.

### /set
Content:
```
{
    uuid: "123e4567-e89b-...",
    amount: 25
}
```

### /fold
Content:
```
{
    uuid: "123e4567-e89b-..."
}
```

## Bot endpoints

### /match_started
Content:
```
{
    match_players: [{
        name: "player1",
        stack: 200
    }, {
        name: "player2",
        stack: 200
    }, {
        name: "player3",
        stack: 200
    }],
    dealer: "player1",
    your_money: 200
}
```

### /round_started
Content:
```
{
    round_number: 1,
    round_players: [{
        name: player1,
        stack: 200
    }, {
        name: player2,
        stack: 200
    }],
    round_dealer: "player1"
}
```

"round_dealer" is used when the actual dealer has folded in the first round.

### /your_turn
Content:
```
{
    minimum_set: 42,
    maximum_set: 200,
    pot: 320,
    your_cards: [7],
    active_players: [{
        name: player1,
        stack: 200
    }, {
        name: player2,
        stack: 200
    }]
}
```

### /player_set
Content:
```
{
    player: "player1",
    amount: 25
}
```

### /player_folded
Content:
```
{
    player: "player1"
}
```

### /showdown
Content:
```
{
    cards: [{
        player1: [6, 8]
    }, {
        player2: [11, 13]
    }]
}
```

Use-cases:

1. Showdown is only send when after the second round there is more than one round_player left.

### /match_finished
Content:
```
{
    pot: 840,
    winners: [player1]
}
```

Use-cases:

1. If a player that is not the active player sends a message, that message is ignored. -> no response.
1. If the active player sends an invalid message (invalid set amount or unknown message) then the player automatically folds (too low amount or too high amount doesn't matter).
1. If the active player does not response after a predefined period of time, the player automatically folds.


### /game_finished
Content:
```
{
    winner: [player1]
}
```
