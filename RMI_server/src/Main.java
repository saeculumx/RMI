import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> normalFunctions = new ArrayList<>();
        ArrayList<String> allFunctions = new ArrayList<>();
        normalFunctions.add("start");
        normalFunctions.add("stop");
        normalFunctions.add("auth");
        allFunctions.add("start");
        allFunctions.add("stop");
        allFunctions.add("auth");
        allFunctions.add("setConfig");
        allFunctions.add("readConfig");
        allFunctions.add("status");
        allFunctions.add("restart");
        allFunctions.add("topQueue");
        allFunctions.add("queue");
        allFunctions.add("print");
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server is running.");
            Server server = new Server();
            String user = "userASL_ApproveAll", email = "test@test.test", password = "password";
            String user1 = "userASL_OnlyNormal", email1 = "test@test.test", password1 = "password";
            // server.addUser(user, email, password,allFunctions);
            // server.addUser(user1, email1, password1,normalFunctions);
            registry.rebind("RMIServer", server);
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e+" At: "+e.getStackTrace()[0].getLineNumber()+" Cause "+e.getCause());
        }

    }
}