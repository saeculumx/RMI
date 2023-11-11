import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper {

    private static DatabaseHelper instance;
    private Connection connection;

    private DatabaseHelper(String dbName) throws SQLException {
        // Connect to requested SQLite database
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        String query = null;
        //Create table in database
        if (dbName.equals("pwDB")) {
            query = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL, password_hash TEXT NOT NULL UNIQUE);";
        } else if (dbName.equals("condimentsDB")) {
            query = "CREATE TABLE IF NOT EXISTS condiments ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, salt TEXT NOT NULL);";
        } else if (dbName.equals("ASL")) {
            query = "CREATE TABLE IF NOT EXISTS functions  (\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    functions TEXT NOT NULL\n" +
                    ")";
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
        if (dbName.equals("condimentsDB")) {
            instance.connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            String query = "CREATE TABLE IF NOT EXISTS condiments ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, salt TEXT NOT NULL);";
            Statement statement = instance.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        if (dbName.equals("pwDB")) {
            instance.connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            String query = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL, password_hash TEXT NOT NULL);";
            Statement statement = instance.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        if (dbName.equals("ASL")) {
            instance.connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            String query = "CREATE TABLE IF NOT EXISTS functions  (\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    functions TEXT NOT NULL\n" +
                    ")";
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

        if (results.next()) {
            res = results.getString("password_hash");
            statement.close();
            return res;
        } else {
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

        if (results.next()) {
            res = results.getString("salt");
            statement.close();
            return res;
        } else {
            statement.close();
            return null;
        }
    }

    public void addUser(String username, String email, String passwordHash) throws SQLException {
        //TODO Check if connected to passwords DB
        String query = "INSERT OR REPLACE INTO users (username, email, password_hash) VALUES (?, ?, ?);";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, passwordHash);
        statement.executeUpdate();

        statement.close();
    }

    public void addFunction(String name, ArrayList<String> functionList) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + "condimentsDB");
        String id_query = "SELECT id FROM condiments WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(id_query);
        statement.setString(1, name);
        String res = "";
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            res = results.getString("id");
            statement.close();
        } else {
            res = "ERR";
            statement.close();
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + "ASL");
        String query = "INSERT INTO functions (id, name, functions) VALUES (?,?,?);";
        PreparedStatement statement1 = connection.prepareStatement(query);
        statement1.setString(1, res);
        statement1.setString(2, name);
        statement1.setObject(3, functionList);
        statement1.executeUpdate();
    }

    public void setFunctions(String user, ArrayList<String> functionList) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + "ASL");
        String query = "UPDATE functions SET functions = ? WHERE name = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, functionList);
        statement.setString(2, user);
        statement.executeUpdate();
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

    public ArrayList<String> getFunctions(String user) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + "ASL");
        String query = "SELECT functions FROM functions WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user);
        ArrayList<String> arr = new ArrayList<>();
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            String s = (String) results.getObject("functions");
            System.out.println(">>Server<< Client function available: " + s);
            String[] items = s.substring(1, s.length() - 1).split(", ");
            arr = new ArrayList<>(Arrays.asList(items));
            statement.close();
            return arr;
        } else {
            statement.close();
            return null;
        }
    }

    public String getUsernameFromToken(String passwordHash) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + "pwDB");
        String query = "SELECT username FROM users WHERE password_hash = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, passwordHash);
        String user = "";
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            user = results.getString("username");
            statement.close();
            return user;
        } else {
            statement.close();
            return null;
        }
    }
}
