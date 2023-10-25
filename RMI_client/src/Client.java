import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client implements RemoteInterface{
    private RemoteInterface server;
    public Client(String user, String password) throws RemoteException, MalformedURLException, NotBoundException {
        RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost:1099/RMIServer");
        server.start();
        TokenObj obj = server.auth(user, password);
        System.out.println(">>Client<< "+obj.id+" // "+obj.uuid);
    }
    @Override
    public String print(String filename, String printer, String token) {
        return null;
    }

    @Override
    public ArrayList<String> queue(String printer, String token) {
        return null;
    }

    @Override
    public Void topQueue(String printer, int job, String token) {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void restart(String token) {

    }

    @Override
    public String status(String printer, String token) {
        return null;
    }

    @Override
    public String readConfig(String parameter, String token) {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, String token) {

    }

    @Override
    public TokenObj auth(String user, String password) throws RemoteException {
        return null;
    }

}
