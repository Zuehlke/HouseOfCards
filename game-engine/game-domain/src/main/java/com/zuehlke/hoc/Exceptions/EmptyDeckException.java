package com.zuehlke.hoc.Exceptions;


public class EmptyDeckException extends RuntimeException {
    public EmptyDeckException(String message) {
        super(message);
    }
}
