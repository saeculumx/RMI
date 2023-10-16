import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server is running.");
            Server server = new Server();
            registry.rebind("RMIServer", server);
            System.out.println("Press Enter to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e);
        }
    }
}