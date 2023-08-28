package klient;

import java.io.Serializable;
import java.rmi.Remote;

public interface ClientInterface extends Remote, Serializable{
    public void sendMsg(String usrname, String msg);
}
