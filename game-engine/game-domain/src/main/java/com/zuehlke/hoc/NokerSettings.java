package com.zuehlke.hoc;

import com.zuehlke.hoc.model.NokerDeck;

public class NokerSettings {
    public final static long INITIAL_CHIPS = 100;
    public final static long BLIND = 5;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = NokerDeck.NUM_CARDS_OF_SINGLE_DECK / CARDS_PER_PLAYER;
}
