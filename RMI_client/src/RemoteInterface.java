import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote {
    String print(String filename, String printer, String token) throws RemoteException;
    ArrayList<String> queue(String printer, String token) throws RemoteException;
    Void topQueue(String printer, int job, String token) throws RemoteException;
    void start() throws RemoteException;
    void stop() throws RemoteException;
    void restart(String token) throws RemoteException;
    String status(String printer, String token) throws RemoteException;
    String readConfig(String parameter, String token) throws RemoteException;
    void setConfig(String parameter, String value, String token) throws RemoteException;
    public TokenObj auth(String user, String password) throws RemoteException;
}
