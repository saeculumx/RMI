import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.ServerRef;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class Server extends UnicastRemoteObject implements RemoteInterface {
    String pwDB = "pwDB";
    String condimentsDB = "condimentsDB";
    ArrayList<TokenObj> tokens = new ArrayList<>();
    public Server() throws RemoteException, ServerNotActiveException {
        super();
    }
    @Override
    public String print(String filename, String printer, TokenObj token) {
        if (checkToken(token))
            System.out.println(filename);
        return filename;
    }

    @Override
    public ArrayList<String> queue(String printer, TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Queue here");
        return null;
    }

    @Override
    public void topQueue(String printer, int job, TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Queue have being moved to top");
    }

    @Override
    public void start() {
        System.out.println(">>Server<< Connection Established");
    }

    @Override
    public void stop() {
        System.out.println(">>Server<< Connection Closed");
    }

    @Override
    public void restart(TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Printer restarting");
    }

    @Override
    public String status(String printer, TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Status");
        return "status";
    }

    @Override
    public String readConfig(String parameter, TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Config");
        return "Config";
    }

    @Override
    public void setConfig(String parameter, String value, TokenObj token) {
        if (checkToken(token))
            System.out.println(">>Server<< Setting Config");
    }

    @Override
    public TokenObj auth(String user, String password) throws RemoteException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

        System.out.println(">>Server<< User ID: " + user + " ,Password: " + password);
        Token token = new Token();
        boolean duplicate_flag = checkDuplicate(user);
        TokenObj tokenobj = new TokenObj(user, token.createUUID(user));
        if (duplicate_flag) {
            if (checkPassword(user, password)) {
                tokens.add(tokenobj);
                System.out.println(">>Server<< User Authentication duplicated, Distributing new token for user: " + user);
                System.out.println(">>Server<< Distributing Token, "+ " Token: "+tokenobj.uuid+" Token array length: " + tokens.size());
                return tokenobj;
            } else {
                System.out.println(">>Server<< Authentication failed");
                return null;
            }
        }
        else {
            if (checkPassword(user, password)) {
                tokens.add(tokenobj);
                System.out.println(">>Server<< " + user + " have be logged in using password: " + password);
                System.out.println(">>Server<< Distributing Token, "+ " Token: "+tokenobj.uuid+" Token array length: " + tokens.size());
                return tokenobj;
            } else {
                System.out.println(">>Server<< Authentication failed");
                return null;
            }
        }
    }

    private boolean checkDuplicate(String user) {
        for (TokenObj t : tokens) {
            // System.out.println(">>122<< "+t.id+" "+user+" "+Objects.equals(t.id, user));
            if(Objects.equals(t.id, user))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String user, String plainPsw) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        DatabaseHelper passwordDbHelper;
        DatabaseHelper condimentsDbHelper;
        String salt, pwFromDb, hashedPw;
        condimentsDbHelper = DatabaseHelper.getInstance("condimentsDB");
        //Get salt from DB if it exists
        salt = condimentsDbHelper.getSaltByUser(user);
        if (salt == null || salt.isEmpty()){
            return false;
        }
        //Hashing the password we have to check
        hashedPw = Hash.hash(plainPsw, Base64.getDecoder().decode(salt));
        //Drop connection to condimentsDB
        condimentsDbHelper.close();

        //Getting the stored hashedPassword
        passwordDbHelper = DatabaseHelper.getInstance("pwDB");
        pwFromDb = passwordDbHelper.getPasswordHashByUser(user);
        passwordDbHelper.close();
        //Check if they're equal
        return pwFromDb.equals(hashedPw);
    }

    public boolean checkToken(TokenObj token){
        //TODO Implement token expiration
        if (token == null) return false;
        for (TokenObj tok:tokens
             ) {
            if (token.uuid.equals(tok.uuid) && token.id.equals(tok.id)) return true;
        }
        return false;
    }

    public void addUser(String user, String email, String password) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        DatabaseHelper db;
        db = DatabaseHelper.getInstance("pwDB");
        //Generate new salt and hash password
        byte[] newSalt = Hash.getNewSalt();
        String hashedPsw = Hash.hash(password, newSalt);
        //Create new user
        db.addUser(user, email, hashedPsw);
        db.close();
        db = DatabaseHelper.getInstance("condimentsDB");
        db.addUserSalt(user, Base64.getEncoder().encodeToString(newSalt));
        db.close();
    }

}

