package com.zuehlke.hoc.rest;

/**
 * Represents a message to confirm or reject a player registration. The competition runner responses with a
 * <code>RegistrationResponse</code> when it receives a <code>RegistrationMessage</code>.
 * The <code>RegistrationResponse</code> tells the bot whether the registration was successful, or the registration
 * phase is closed or the submitted player name or uri is already taken.
 *
 * @author Lukas Hofmaier
 */
public class RegistrationResponse {

    private Result registrationResult;

    public Result getRegistrationResult() {
        return registrationResult;
    }

    public void setRegistrationResult(Result registrationResult) {
        this.registrationResult = registrationResult;
    }

    private enum Result {
        CONFIRMATION, NAME_ALREADY_TAKEN, URI_ALREADY_TAKEN, REGISTRATION_CLOSED
    }
}
