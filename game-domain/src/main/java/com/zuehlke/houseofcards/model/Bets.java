package com.zuehlke.houseofcards.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Bets} class provides the functionality to hold the status of the bets in a match.
 * It can be used in all rounds by resetting with the {@link #startNextBetRound()} method.
 */
public class Bets {

    private final Map<String, Long> bets = new HashMap<>();
    private final Set<String> foldedPlayer = new HashSet<>();
    private final Set<String> playerCalled = new HashSet<>();
    private long highestBet = 0;


    public long getTotalPot(){
        return bets.values().stream().mapToLong(l -> l).sum();
    }

    public void startNextBetRound(){
        playerCalled.clear();
    }

    public void playerFolds(Player player) {
        foldedPlayer.add(player.getName());
        playerCalled.remove(player.getName());
    }

    public void playerCalls(Player player){
        requiresPlayerHasNotAlreadyFolded(player);
        requiemsPlayerHasEnoughChipsToCall(player);
        player.setChipsStack(player.getChipsStack() - neededChipsToCall(player));
        bets.put(player.getName(), highestBet);
        playerCalled.add(player.getName());
    }

    private void requiemsPlayerHasEnoughChipsToCall(Player player) {
        if(player.getChipsStack() < neededChipsToCall(player)){
            throw new RuntimeException("Player has to less chips!");
        }
    }

    public long neededChipsToCall(Player player) {
        return highestBet - getPlayersBet(player);
    }

    public void playerRaise(Player player, long raise){
        requiresPlayerHasNotAlreadyFolded(player);
        requiersPlayerHasEnoughtChipsToRaise(player, raise);
        player.setChipsStack(player.getChipsStack()- neededChipsToRaise(player, raise));
        bets.put(player.getName(), highestBet + raise);
        highestBet = highestBet + raise;
        playerCalled.clear();
        playerCalled.add(player.getName());
    }

    private void requiersPlayerHasEnoughtChipsToRaise(Player player, long raise) {
        if(player.getChipsStack() < neededChipsToRaise(player, raise)){
            throw new RuntimeException("Player has to less chips!");
        }
    }

    private long neededChipsToRaise(Player player, long raise) {
        return (highestBet - getPlayersBet(player)) + raise;
    }

    private void requiresPlayerHasNotAlreadyFolded(Player player){
        if(foldedPlayer.contains(player.getName())){
            throw new RuntimeException("Player has already folded!");
        }
    }

    private long getPlayersBet(Player player){
        return bets.containsKey(player.getName()) ? bets.get(player.getName()) : 0;
    }

    public boolean playerHasFolded(Player player) {
        return foldedPlayer.contains(player.getName());
    }

    public boolean playerHasCalled(Player player) {
        return playerCalled.contains(player.getName());
    }
}
