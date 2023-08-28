package klient;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;
import java.util.TreeMap;

public class ServImpl extends RemoteServer implements ServerInterface{

    Map<String,ClientInterface> clients;

    public ServImpl() {
        clients = new TreeMap<>();
    }

    public static ServImpl getServImpl() {
        if (servImpl == null) servImpl = new ServImpl();
        return servImpl;
    }
    private static ServImpl servImpl;

    @Override
    public String sendMsg(String msg) {
        clients.forEach((u, c) -> {     //formatting client-side
            c.sendMsg(u, msg);
        });
        return null;
    }

    @Override
    public boolean connect(Object client, String usrname) throws ServerNotActiveException, RemoteException {
        if (client instanceof ClientInterface)
        clients.put(getClientHost(), (ClientInterface) client);
        else return false;
        return true;
    }
    
}
