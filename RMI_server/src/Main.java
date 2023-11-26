import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> normalFunctions = new ArrayList<>();
        ArrayList<String> admin_functions = new ArrayList<>();
        ArrayList<String> user_functions = new ArrayList<>();
        ArrayList<String> powerUser_functions = new ArrayList<>();
        ArrayList<String> tec_functions = new ArrayList<>();

        normalFunctions.add("start");
        normalFunctions.add("stop");
        normalFunctions.add("auth");

        admin_functions.add("setConfig");
        admin_functions.add("readConfig");
        admin_functions.add("status");
        admin_functions.add("restart");
        admin_functions.add("topQueue");
        admin_functions.add("queue");
        admin_functions.add("print");
        admin_functions.addAll(normalFunctions);

        user_functions.add("queue");
        user_functions.add("print");
        user_functions.addAll(normalFunctions);

        powerUser_functions.add("queue");
        powerUser_functions.add("print");
        powerUser_functions.add("restart");
        powerUser_functions.add("topQueue");
        powerUser_functions.addAll(normalFunctions);

        tec_functions.add("restart");
        tec_functions.add("readConfig");
        tec_functions.add("setConfig");
        tec_functions.add("topQueue");
        tec_functions.addAll(normalFunctions);

        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server is running.");
            Server server = new Server();
            String user_Bob = "bob", email = "", password = "password";
            String user_George = "George";
            String user_Henry = "Henry";
            String user_Ida = "Ida";
            String user_Cecilia = "Cecilia";

            // server.addUser(user_Bob, email, password,tec_functions);
            server.addUser(user_George, email, password,tec_functions);
            server.addUser(user_Henry, email, password,normalFunctions);
            server.addUser(user_Ida, email, password,powerUser_functions);
            // server.addUser(user_Cecilia, email, password,powerUser_functions);

            server.removeUserFunctions(user_Bob);


            registry.rebind("RMIServer", server);
        } catch (Exception e) {
            System.out.println(">>ERR:<< " + e + " At: " + e.getStackTrace()[0].getLineNumber() + " Cause " + e.getCause());
        }

    }
}