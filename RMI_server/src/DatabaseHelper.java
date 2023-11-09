import java.sql.*;

public class DatabaseHelper {

    private static DatabaseHelper instance;
    private Connection connection;

    private DatabaseHelper(String dbName) throws SQLException {
        // Connect to requested SQLite database
        connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
        String query = null;
        //Create table in database
        if (dbName.equals("pwDB")){
            query = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL, password_hash TEXT NOT NULL UNIQUE);";
        }
        else if (dbName.equals("condimentsDB")){
            query = "CREATE TABLE IF NOT EXISTS condiments ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, salt TEXT NOT NULL);";
        }
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    public void close() throws SQLException {
        connection.close();
    }

    public static DatabaseHelper getInstance(String dbName) throws SQLException {
        if (instance == null) {
            instance = new DatabaseHelper(dbName);
        }

        //To remove after testing//
        if (dbName.equals("condimentsDB")){
            instance.connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
            String query = "CREATE TABLE IF NOT EXISTS condiments ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, salt TEXT NOT NULL);";
            Statement statement = instance.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        if (dbName.equals("pwDB")){
            instance.connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
            String query = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL, password_hash TEXT NOT NULL);";
            Statement statement = instance.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        //////
        return instance;
    }

    // Public methods to perform operations on the SQLite database

    public String getPasswordHashByUser(String user) throws SQLException {
        String query = "SELECT password_hash FROM users WHERE username = ?;";
        String passwordHash = "", res;
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            res = results.getString("password_hash");
            statement.close();
            return res;
        }
        else{
            statement.close();
            return null;
        }
    }

    public String getSaltByUser(String user) throws SQLException {
        //TODO Check if connected to salt DB
        String query = "SELECT salt FROM condiments WHERE username = ?;";
        String res;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            res = results.getString("salt");
            statement.close();
            return res;
        }
        else{
            statement.close();
            return null;
        }
    }

    public void addUser(String username, String email, String passwordHash) throws SQLException {
        //TODO Check if connected to passwords DB
        String query = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?);";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, passwordHash);
        statement.executeUpdate();

        statement.close();
    }

    public void addUserSalt(String user, String salt) throws SQLException {
        //TODO Check if connected to condiments DB
        String query = "INSERT INTO condiments (username, salt) VALUES (?, ?);";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user);
        statement.setString(2, salt);
        statement.executeUpdate();

        statement.close();
    }

}
