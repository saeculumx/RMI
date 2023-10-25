import java.io.Serializable;

public class TokenObj implements Serializable {
    String id;
    String uuid;
    public TokenObj(String id, String uuid){
        this.id = id;
        this.uuid = uuid;
    }

}