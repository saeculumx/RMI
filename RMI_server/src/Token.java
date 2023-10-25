import java.util.ArrayList;
import java.util.UUID;

public class Token {
    String createUUID(String id){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
