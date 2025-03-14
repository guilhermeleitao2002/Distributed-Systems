package pt.tecnico.ttt.server;

import java.util.ArrayList;
import java.util.List;

public class ServiceEntry {
    private String serviceName;
    private List<ServerEntry> servers = new ArrayList<>();

    public ServiceEntry(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public List<ServerEntry> getServers() {
        return servers;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void addServer(ServerEntry server) {
        servers.add(server);
    }
}