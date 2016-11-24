package com.zuehlke.houseofcards.Exceptions;


public class EmptyDeckException extends RuntimeException {
    public EmptyDeckException(String message) {
        super(message);
    }
}
