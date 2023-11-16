
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Scanner scanner = new Scanner(System.in);
            System.out.println("RMI Server is running.");
            Server server = new Server();
            int option = 0;
            //String user = "user", email = "test@test.tests", password = "cipolla";
            int  roleId = 2;
            //server.addUser(user, email, password, roleId);//We can add a new user specifying the role
            //server.removeUser("user");                    //We can remove a user from both DBs
            //server.updateUserRole("user", 4);   //We can update the role for a given user
            registry.rebind("RMIServer", server);
            //MENU
            while (option == 0){
                System.out.println("///////_Server Panel Control_///////\n");
                System.out.println("Select a fuction:\n");
                System.out.println("1 - Add a new user");
                System.out.println("2 - Remove a user");
                System.out.println("3 - Update a user's role");
                option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1){
                    System.out.println("Insert the username for the new user:");
                    String username = scanner.nextLine();
                    System.out.println("Insert an email for the new user:");
                    String email = scanner.nextLine();
                    System.out.println("Choose a password for the new user:");
                    String password = scanner.nextLine();
                    System.out.println("What role should this user have?");
                    Map<Integer, String> rolesMap = server.getAllRoles();
                    for (Integer roleID : rolesMap.keySet()) {
                        String roleName = rolesMap.get(roleID);
                        System.out.println(roleID + ": " + roleName);
                    }
                    int RoleID = scanner.nextInt();
                    scanner.nextLine();
                    server.addUser(username, email, password, RoleID);
                    System.out.println("User successfully added!\n\n");
                    option = 0;
                }
                else if (option == 2){
                    System.out.println("Insert the user's username you would like to remove:");
                    String username = scanner.nextLine();
                    server.removeUser(username);
                    System.out.println("User successfully removed!\n\n");
                    option = 0;
                }
                else if (option == 3) {
                    System.out.println("Insert the user's username you would like to update:");
                    String username = scanner.nextLine();
                    int RoleId = server.getUserRole(username);
                    //String RoleName = server.getUserRole(username).roleName;
                    System.out.println("This user has role " + RoleId + "\n\n");
                    System.out.println("Insert the new role ID for " + username + " user");
                    RoleId = scanner.nextInt();
                    scanner.nextLine();
                    server.updateUserRole(username, RoleId);
                    System.out.println("User successfully removed!\n\n");
                    option = 0;
                }
            }
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e);
        }
    }
}