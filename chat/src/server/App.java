package server;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class App {
  public static void main(String[] args) {
    try {

        ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) ServImpl.getServImpl(), 12345);
        Registry reg = LocateRegistry.createRegistry(12345);
        reg.rebind("Chat", stub);

      System.out.println("it doin the thing");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
