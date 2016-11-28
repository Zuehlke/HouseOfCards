package com.zuehlke.hoc.liveview;

/**
 * Created by ralo on 28/11/16.
 */
public class SimpleViewerMessage {
    private String message = "";

    public String getMessage() { return this.message; }

    SimpleViewerMessage(String message) {
        this.message = message;
    }
}
