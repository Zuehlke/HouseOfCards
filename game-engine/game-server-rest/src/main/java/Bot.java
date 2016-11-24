import java.util.Objects;

public class Bot {

    private String name;
    private String hostname;
    private int port;

    public String getName() {
        return name;
    }

    public Bot setName(String name) {
        this.name = name;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public Bot setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Bot setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { return false; }
        else if (other == this) { return true; }
        else if (!(other instanceof Bot)) { return false; }
        else {
            Bot otherBot = (Bot) other;
            return Objects.equals(getName(), otherBot.getName()) &&
                    Objects.equals(getHostname(), otherBot.getHostname()) &&
                    Objects.equals(getPort(), otherBot.getPort());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHostname(), getPort());
    }
}
