import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client implements RemoteInterface{
    public Client() throws RemoteException, MalformedURLException, NotBoundException {
        Naming.lookup("rmi://localhost:1099/start");
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
        System.out.println("Client");
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
