import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote{
    public String print(String filename, String printer, TokenObj token) throws RemoteException;
    public ArrayList<String> queue(String printer, TokenObj token) throws RemoteException;
    public Void topQueue(String printer, int job, TokenObj token) throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;
    public void restart(TokenObj token) throws RemoteException;
    public String status(String printer, TokenObj token) throws RemoteException;
    public String readConfig(String parameter, TokenObj token) throws RemoteException;
    public void setConfig(String parameter, String value, TokenObj token) throws RemoteException;
    public TokenObj auth(String user, String password) throws RemoteException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;
}
