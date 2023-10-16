import java.rmi.Remote;
import java.util.ArrayList;

public interface RemoteInterface extends Remote {
    String print(String filename, String printer);
    ArrayList<String> queue(String printer);
    Void topQueue(String printer, int job);
    void start();
    void stop();
    void restart();
    String status(String printer);
    String readConfig(String parameter);
    void setConfig(String parameter, String value);
}
