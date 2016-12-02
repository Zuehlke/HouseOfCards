package com.zuehlke.hoc.model;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.PlayerNotifierAdapter;

import java.util.List;


/**
 * This class represents a round of the Noker game.
 * A Noker game has two rounds. In each round, the players
 * are given a card from the deck.
 */
public class Round {

    private PlayerNotifierAdapter notifier;
    private BetIterator betIterator;
    private Player turnOfPlayer;
    private Deck deck;
    private Bets bets;


    public Round(List<Player> players, int firstPlayerIndex, Deck deck, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.deck = deck;
        this.bets = new Bets();
        this.betIterator = new BetIterator(players, firstPlayerIndex, bets);
    }

    private Round(){}

    public Round createSecondRound(){
        Round round = new Round();
        round.notifier = notifier;
        round.betIterator = betIterator;
        betIterator.reset();
        round.deck = deck;
        bets.startNextBetRound();
        round.bets = bets;
        return round;
    }

    public void startRound() {
        notifier.broadcastRoundStarts();
        dealCard();
        turnOfPlayer = betIterator.next();
        notifier.askPlayerForAction(turnOfPlayer.getName(), 0);
    }

    public void dealCard() {
        betIterator.getPlayers().forEach(p -> {
            if(!bets.playerHasFolded(p)) {
                int card = deck.drawCard();
                p.addCard(card);
                notifier.sendCardInfoToPlayer(p.getName(), card);
            };
        });
    }


    public boolean isFinished(){
        return !betIterator.hasNext();
    }

    public Bets getBets() {
        return bets;
    }

    public void playerFold(Player player){
        if (isPlayersTurn(player)) {
            bets.playerFolds(player);
            notifier.broadcastPlayerFolded(player);
            notifyNextPlayerOrBroadcastFinishEvent();
        }
    }

    public void playerCall(Player player){
        if (isPlayersTurn(player)) {
            bets.playerCalls(player);
            notifier.broadcastPlayerCalled(player);
            notifyNextPlayerOrBroadcastFinishEvent();
        }
    }

    public void playerRaise(Player player, long raiseAmount){
        if (isPlayersTurn(player)) {
            bets.playerRaise(player, raiseAmount);
            notifier.broadcastPlayerRaised(player, raiseAmount);
            notifyNextPlayerOrBroadcastFinishEvent();
        }
    }


    private boolean isPlayersTurn(Player player) {
        return player.getName().equals(turnOfPlayer.getName());
    }

    private void notifyNextPlayerOrBroadcastFinishEvent() {
        if(betIterator.hasNext()){
            turnOfPlayer = betIterator.next();
            notifier.askPlayerForAction(turnOfPlayer.getName(), bets.neededChipsToCall(turnOfPlayer));
        }else{
            notifier.broadcastRoundFinished();
        }
    }

    public Player getCurrentPlayer() {
        return  turnOfPlayer;
    }
}
