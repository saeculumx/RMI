import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("RMI Server is running.");
            Server server = new Server();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RMIServer", server);
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e);
        }
    }
}