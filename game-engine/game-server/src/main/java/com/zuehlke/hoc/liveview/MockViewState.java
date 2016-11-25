package com.zuehlke.hoc.liveview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralo on 25/11/16.
 */
class MockViewState {
    static int counter = 50;

    private int pot = 44;
    private MockPlayer currentPlayer;
    private MockPlayer dealer;
    private List<MockPlayer> players;

    MockViewState(int changingVariable) {
        this.pot = changingVariable*10;
        MockPlayer p1 = new MockPlayer("MARTA", "KK", 15, changingVariable);
        MockPlayer p2 = new MockPlayer("FRIDA", "72", changingVariable/2, 2);
        MockPlayer p3 = new MockPlayer("BERTA", "A2", 13, 37);
        MockPlayer p4 = new MockPlayer("HANNA", "D4", changingVariable, changingVariable/3);

        this.players = new ArrayList<>();
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
        this.players.add(p4);

        this.currentPlayer = players.get(changingVariable % 3);
        this.dealer = players.get((changingVariable/3) % 3);

    }

    public int getPot() {
        return pot;
    }

    public MockPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public MockPlayer getDealer() {
        return dealer;
    }

    public List<MockPlayer> getPlayers() {
        return players;
    }


    private static class MockPlayer {
        public String getCards() {
            return cards;
        }

        public int getMoney() {
            return money;
        }

        public int getBet() {
            return bet;
        }

        public String getName() {
            return name;
        }

        private String cards;
        private int money;
        private int bet;
        private String name;

        MockPlayer(String name, String cards, int money, int bet) {
            this.name = name;
            this.cards = cards;
            this.money = money;
            this.bet = bet;
        }
    }
}

