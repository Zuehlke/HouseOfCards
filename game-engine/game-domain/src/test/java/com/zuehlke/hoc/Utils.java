package com.zuehlke.hoc;


import com.zuehlke.hoc.model.Player;

import java.util.Arrays;
import java.util.List;



public class Utils {

    public static List<Player> loadDummyPlayers() {
        return Arrays.asList(new Player("Tobi"), new Player("Miki"), new Player("Riki"));
    }
}
