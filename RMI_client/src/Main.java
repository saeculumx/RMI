import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean not_authenticated = true;
        String user = "";
        String password = "";
        try {
            Scanner scanner = new Scanner(System.in);
            try {
                while (not_authenticated) {
                    System.out.println("Please input a username (\"user\")");
                    user = scanner.nextLine();
                    System.out.println("Please input the password (\"password\")");
                    password = scanner.nextLine();
                    Client client = new Client(user,password);
                    not_authenticated = !client.is_auth();
                }
            } catch(IllegalStateException | NoSuchElementException e) {
                // System.in has been closed
                System.out.println("System.in was closed; exiting");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}