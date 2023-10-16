import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements RemoteInterface {
    public Server() throws RemoteException{
        super();
    }
    @Override
    public String print(String filename, String printer) {
        return null;
    }

    @Override
    public ArrayList<String> queue(String printer) {
        return null;
    }

    @Override
    public Void topQueue(String printer, int job) {
        return null;
    }

    @Override
    public void start() {
        System.out.println(">>Server<< Connection Established");
    }

    @Override
    public void stop() {

    }

    @Override
    public void restart() {

    }

    @Override
    public String status(String printer) {
        return null;
    }

    @Override
    public String readConfig(String parameter) {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) {

    }
}
