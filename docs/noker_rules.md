# Noker rules

Noker is based on the traditional Texas Hold’em Poker game. It’s main objective is to simplify the game mechanics
therefore facilitating the development of bots.

A game begins with an empty table and a defined number of players. Each player holds an amount of chips which can be
used for betting. One match consists of two consecutive rounds of betting. Matches are played until one player owns the
total amount of chips of the game.

In the first round, each player is given one card from the deck (by the dealer). Next, the first player makes a bet of
0 or more chips. Each player in turn has three possibilities:

* “_Call_” the bet by putting the same amount of chips into the pot
* “_Rise_” the bet by putting additional chips into the pot (the raise limit cannot be exceeded, which is equal to the amount of chips of the poorest player)
* “_Fold_”, which means that he puts no chips in the pot, discards his hand, and is out of the current game. When a player folds, he loses any chips he has put into the pot.

The first round ends when every (non folded) player has put in exactly as many chips as his predecessors.
On the second round each player receives an additional card from the deck and the betting procedure from the first round
is repeated. Round two ends with a “showdown”, which means that the player who last rose the bet shows his two cards face up on the table.
Every other player can choose to show his two cards as well. The player with the best hand wins the total amount of chips from the pot.
A hand is rated by the height of its two cards considering that pairs have a higher value than single cards. If it's a tie (unentschieden),
the pot is split and distributed among the winners of the match.

In a Noker game every player is required to contribute an initial (small) amount of chips. This rule prevents that players
fold games waiting for a high-valued first card. This amount goes directly into the pot at the beginning of a match.
