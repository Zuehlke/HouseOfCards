package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.PlayerNotifierAdapter;

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
        requieresPlayersTurn(player);
        bets.playerFolds(player);
        notifier.broadcastPlayerFolded(player);
        notifyNextPlayerOrBroadcastFinishEvent();
    }

    public void playerCall(Player player){
        requieresPlayersTurn(player);
        bets.playerCalls(player);
        notifier.broadcastPlayerCalled(player);
        notifyNextPlayerOrBroadcastFinishEvent();
    }

    public void playerRaise(Player player, long raise){
        requieresPlayersTurn(player);
        bets.playerRaise(player, raise);
        notifier.broadcastPlayerRaised(player, raise);
        notifyNextPlayerOrBroadcastFinishEvent();
    }

    private void notifyNextPlayerOrBroadcastFinishEvent() {
        if(betIterator.hasNext()){
            turnOfPlayer = betIterator.next();
            notifier.askPlayerForAction(turnOfPlayer.getName(), bets.neededChipsToCall(turnOfPlayer));
        }else{
            notifier.broadcastRoundFinished();
        }
    }

    private void requieresPlayersTurn(Player player) {
        if(!player.getName().equals(turnOfPlayer.getName())){
            throw new RuntimeException("Not the turn of the player: "+player.getName());
        }
    }

    public Player getCurrentPlayer() {
        return  turnOfPlayer;
    }
}
