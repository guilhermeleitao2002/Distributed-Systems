package pt.tecnico.ttt.server;

public class ServerEntry {
    private String qualifier;
    private String address;

    public ServerEntry(String qualifier, String hostPort) {
        this.qualifier = qualifier;
        this.address = hostPort;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getAddress() {
        return address;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setHostPort(String hostPort) {
        this.address = hostPort;
    }
}
