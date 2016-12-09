package com.zuehlke.hoc.model;

import com.zuehlke.hoc.PlayerNotifierAdapter;

import java.util.List;

import static com.zuehlke.hoc.model.Round.RoundNumber.FIRST_ROUND;
import static com.zuehlke.hoc.model.Round.RoundNumber.SECOND_ROUND;


/**
 * This class represents a round of a Noker game match.
 * A match has two rounds. In each round, the players
 * are given one card from the deck.
 */
public class Round {

    public enum RoundNumber {
        FIRST_ROUND, SECOND_ROUND;
    }

    private PlayerNotifierAdapter notifier;
    private BetIterator betIterator;
    private Player turnOfPlayer;
    private Deck deck;

    private RoundNumber currentRound = FIRST_ROUND;
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
        round.currentRound = SECOND_ROUND;
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
            }
        });
    }

    public RoundNumber getCurrentRound() {
        return currentRound;
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
            notifier.broadcastPlayerSet(player, bets.neededChipsToCall(player));
            notifyNextPlayerOrBroadcastFinishEvent();
        }
    }

    public void playerRaise(Player player, long raiseAmount){
        if (isPlayersTurn(player)) {
            bets.playerRaise(player, raiseAmount);
            notifier.broadcastPlayerSet(player, raiseAmount);
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
}
