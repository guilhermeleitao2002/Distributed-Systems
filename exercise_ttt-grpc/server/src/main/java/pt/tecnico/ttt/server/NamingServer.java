package pt.tecnico.ttt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamingServer {
    private Map<String, ServiceEntry> services = new HashMap<>();

    public NamingServer() {
    }

    public void addService(String serviceName) {
        services.put(serviceName, new ServiceEntry(serviceName));
    }

    public void register(String serviceName, String qualifier, String hostPort) {
        addService(serviceName);
        services.get(serviceName).addServer(new ServerEntry(qualifier, hostPort));
    }

    public void delete(String serviceName, String hostPort) {
        if (services.containsKey(serviceName)) {
            for (ServerEntry server : services.get(serviceName).getServers()) {
                if (server.getAddress().equals(hostPort)) {
                    services.get(serviceName).getServers().remove(server);
                    break;
                }
            }
        }
    }

    public List<ServerEntry> lookup(String serviceName, String qualifier) {
        if (services.containsKey(serviceName)) {
            for (ServerEntry server : services.get(serviceName).getServers()) {
                if (server.getQualifier().equals(qualifier)) {
                    ArrayList<ServerEntry> result = new ArrayList<>();
                    result.add(server);
                    return result;
                }
            }
        }
        return services.get(serviceName).getServers();
    }
}
