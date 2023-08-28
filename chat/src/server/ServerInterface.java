package server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

//RemoteExc, ServNotActExc

public interface ServerInterface extends Remote, Serializable {
    public String sendMsg(String msg) throws ServerNotActiveException, RemoteException;
    public boolean connect(Object client, String usrname) throws ServerNotActiveException, RemoteException;
}
