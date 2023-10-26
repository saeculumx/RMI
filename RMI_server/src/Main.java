import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server is running.");
            Server server = new Server();
            //String user = "test", email = "test@test.test", password = "mammamia";
            //server.addUser(user, email, password);
            registry.rebind("RMIServer", server);
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e);
        }
    }
}