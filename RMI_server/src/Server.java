import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.ServerRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements RemoteInterface {
    ArrayList<TokenObj> tokens = new ArrayList<>();
    public Server() throws RemoteException, ServerNotActiveException {
        super();
    }
    @Override
    public String print(String filename, String printer, String token) {
        return null;
    }

    @Override
    public ArrayList<String> queue(String printer, String token) {
        return null;
    }

    @Override
    public Void topQueue(String printer, int job, String token) {
        return null;
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
    public void restart(String token) {

    }

    @Override
    public String status(String printer, String token) {
        return null;
    }

    @Override
    public String readConfig(String parameter, String token) {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, String token) {

    }

    @Override
    public TokenObj auth(String user, String password) throws RemoteException {
        System.out.println(">>Server<< User ID: "+user+" ,Password: "+password);
        boolean authStatus = true;
        Token token = new Token();
        TokenObj tokenobj = new TokenObj(user,token.createUUID(user));
        tokens.add(tokenobj);
        System.out.println(">>Server<< Token array length: "+tokens.size());
        //checkPassword();
        if (true){//checkPassword()) {
            return tokenobj;
        }
        else{
            return null;
        }

    }
    public boolean checkPassword(){
        //TODO
        return true;
    }

    public boolean checkToken(TokenObj token){
        return tokens.contains(token);
    }

}

