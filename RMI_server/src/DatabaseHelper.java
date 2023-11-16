import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void addUser(String username, String email, String passwordHash, int roleId) throws SQLException {
        //TODO Check if connected to passwords DB
        String query = "INSERT INTO users (username, email, password_hash, RoleID) VALUES (?, ?, ?, ?);";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, passwordHash);
        statement.setInt(4, roleId);
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

    public ArrayList<String> getFunctions(String username) throws SQLException {
        ArrayList<String> functions = new ArrayList<>();
        String query = "WITH RECURSIVE RoleHierarchy AS (\n" +
                "    -- Anchor member: Select the role for the given username\n" +
                "    SELECT Users.RoleID\n" +
                "    FROM Users\n" +
                "             INNER JOIN Roles ON Users.RoleID = Roles.RoleID\n" +
                "    WHERE Users.Username = ?\n" +
                "\n" +
                "    UNION ALL\n" +
                "\n" +
                "    -- Recursive member: Select child roles\n" +
                "    SELECT R.RoleID\n" +
                "    FROM Roles AS R\n" +
                "             INNER JOIN RoleHierarchy AS RH ON R.ParentRoleID = RH.RoleID\n" +
                ")\n" +
                "-- Get functions for roles in the hierarchy\n" +
                "SELECT DISTINCT RF.FunctionName\n" +
                "FROM RoleFunctions AS RF\n" +
                "WHERE RF.RoleID IN (SELECT RoleID FROM RoleHierarchy);";

        // Prepare the statement and set the parameter
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);

        // Execute the query and retrieve the results
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String result = results.getString(1);
            functions.add(result);
        }

        // Close the statement and return the functions
        statement.close();
        return functions;
    }

    public void removeUser(String username) throws SQLException {
        //TODO Check if connected to passwords DB
        String query = "DELETE FROM users WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.executeUpdate();
        statement.close();
    }

    public void removeUserSalt(String username) throws SQLException {
        //TODO Check if connected to condiments DB
        String query = "DELETE FROM condiments WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.executeUpdate();
        statement.close();
    }

    public void updateUserRole(String username, int roleID) throws SQLException {
        String query = "UPDATE users SET RoleID = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, roleID);
        statement.setString(2, username);
        statement.executeUpdate();
        statement.close();
    }

    public Map<Integer, String> getAllRoles() throws SQLException {
        Map<Integer, String> rolesMap = new HashMap<>();
        String query = "SELECT RoleID, Name FROM Roles";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int roleID = results.getInt(1);
            String roleName = results.getString(2);

            rolesMap.put(roleID, roleName);
        }

        statement.close();
        return rolesMap;
    }

    public int getUserRole(String username) throws SQLException {
        String query = "SELECT RoleID FROM Users WHERE Username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            throw new IllegalArgumentException("User " + username + " does not exist");
        }
    }
}
