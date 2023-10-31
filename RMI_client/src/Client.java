import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client{
    //DOES THE CLIENT HAVE TO IMPLEMENTS REMOTE INTERFACE??? I DON'T THINK SO...
    public Client(String user, String password) throws RemoteException, MalformedURLException, NotBoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost:1099/RMIServer");
        server.start();
        TokenObj obj = server.auth(user, password);
        if (obj != null){
            System.out.println(">>Client<< "+obj.id+" // "+obj.uuid);
        }
        else {
            System.out.println(">>Client<< Wrong password or username");
        }
        //Test printing with authentication
        server.print(">>Client Requesting<< I managed to print!", "genericPrinter", obj);
    }
}
