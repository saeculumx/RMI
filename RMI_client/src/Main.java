import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String user = "user";
            String password = "password";
            System.out.println(">>Client<< Connecting to server");
            System.out.println(">>Client<< Server Connected");
            Client client = new Client(user,password);
            //
            Scanner scanner = new Scanner(System.in);
            try {
                while (true) {
                    System.out.println("Please input a line to disconnect");
                    long then = System.currentTimeMillis();
                    String line = scanner.nextLine();
                    long now = System.currentTimeMillis();
                    System.out.printf("Waited %.3fs for user input%n", (now - then) / 1000d);
                    System.out.printf("User input was: %s%n", line);
                }
            } catch(IllegalStateException | NoSuchElementException e) {
                // System.in has been closed
                System.out.println("System.in was closed; exiting");
            }
            //
        } catch (Exception e) {
            System.out.println(">>ERR:<< "+e);
        }
    }
}