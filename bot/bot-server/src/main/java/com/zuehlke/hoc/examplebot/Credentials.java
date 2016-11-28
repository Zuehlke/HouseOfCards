package com.zuehlke.hoc.examplebot;

/**
 * Represent the name of the player. Additionally it defines the uri and TCP port used to receives messages from the
 * competition runner.
 *
 * @author Lukas Hofmaier
 */
public class Credentials {

    public Credentials(String name, String hostname, int port){
        this.name = name;
        this.hostname = hostname;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    private final String name;
    private final String hostname;
    private final int port;
}
