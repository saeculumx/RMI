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
    TokenObj token;
    public Client(String user, String password) throws RemoteException, MalformedURLException, NotBoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        RemoteInterface server = (RemoteInterface) Naming.lookup("rmi://localhost:1099/RMIServer");
        System.out.println(">>Client<< Connecting to server");
        server.start();
        System.out.println(">>Client<< Server Connected");
        token = server.auth(user, password);
        if (token != null){
            //System.out.println(">>Client<< " + "Token is: "+token.id+" - "+token.uuid);
        }
        else {
            System.out.println(">>Client<< Wrong password or username");
        }
        //Test printing with authentication
        System.out.println(">>Server said<< " + "\"" + server.print(">>Client Requesting<< I want to print", "genericPrinter", token)+ "\"");
        System.out.println(">>Server said<< " + "\"" + server.readConfig(">>Client Requesting<< I managed to print!", token)+ "\"");
    }

    public boolean is_auth(){
        return token != null;
    }
}
