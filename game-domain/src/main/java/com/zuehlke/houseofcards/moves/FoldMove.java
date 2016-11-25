package com.zuehlke.houseofcards.moves;

import com.zuehlke.houseofcards.State;
import com.zuehlke.houseofcards.model.Round;
import com.zuehlke.houseofcards.moves.Move;

public class FoldMove implements Move {

    @Override
    public boolean isValid(Round currentRound) {
        return true;    // TODO: revise
    }

    @Override
    public void execute(Round currentRound) {
        // TODO: remove current player from the current match
    }
}
