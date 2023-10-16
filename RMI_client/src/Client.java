import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client implements RemoteInterface{
    private RemoteInterface server;
    public Client() throws RemoteException, MalformedURLException, NotBoundException {
        RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost:1099/RMIServer");
        server.start();
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
