public class Main {
    public static void main(String[] args) {
        try {

            System.out.println("Connecting");
            Client client = new Client();
            System.out.println("Connected");
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}