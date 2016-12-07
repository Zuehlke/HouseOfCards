package com.zuehlke.hoc.liveview;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * The {@link GameHistoryService} stores and provides all the past game messages.
 */
@Service
public class GameHistoryService {

    private List<String> gameMessages;

    public GameHistoryService() {
        gameMessages = new ArrayList<>();
    }

    public void addMessage(String message) {
        gameMessages.add(message);
    }

    public List<String> getGameMessages() {
        return gameMessages;
    }
}
