package com.zuehlke.hoc.rest;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class RegisterMessageTest {

    @Test
    public void testValidation() {
        RegisterMessage message = new RegisterMessage();
        message.setHostname("localhost");
        message.setPlayerName("Mike");
        message.setPort(4356);

        Optional<String> validate = message.validate();
        assertFalse(validate.isPresent());
    }

    @Test
    public void testNoHostAndNull() {
        RegisterMessage message = new RegisterMessage();
        message.setHostname("");
        message.setPlayerName("Mike");
        message.setPort(4356);

        Optional<String> validate = message.validate();
        assertTrue(validate.isPresent());

        message = new RegisterMessage();
        message.setPlayerName("Mike");
        message.setPort(4356);

        validate = message.validate();
        assertTrue(validate.isPresent());
    }

    @Test
    public void testNoPlayerNameAndNull() {
        RegisterMessage message = new RegisterMessage();
        message.setHostname("localhost");
        message.setPlayerName("");
        message.setPort(4356);

        Optional<String> validate = message.validate();
        assertTrue(validate.isPresent());

        message = new RegisterMessage();
        message.setHostname("localhost");
        message.setPort(4356);

        validate = message.validate();
        assertTrue(validate.isPresent());
    }

    @Test
    public void testInvalidPort() {
        RegisterMessage message = new RegisterMessage();
        message.setHostname("localhost");
        message.setPlayerName("Mike");
        message.setPort(99999999);

        Optional<String> validate = message.validate();
        assertTrue(validate.isPresent());
    }
}