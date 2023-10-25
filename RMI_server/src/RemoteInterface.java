import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote{
    public String print(String filename, String printer, String token) throws RemoteException;
    public ArrayList<String> queue(String printer, String token) throws RemoteException;
    public Void topQueue(String printer, int job, String token) throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;
    public void restart(String token) throws RemoteException;
    public String status(String printer, String token) throws RemoteException;
    public String readConfig(String parameter, String token) throws RemoteException;
    public void setConfig(String parameter, String value, String token) throws RemoteException;
    public TokenObj auth(String user, String password) throws RemoteException;
}
