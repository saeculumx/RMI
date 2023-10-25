import java.sql.*;

public class DatabaseHelper {

    private static DatabaseHelper instance;
    private Connection connection;

    private DatabaseHelper() throws SQLException {
        // Connect to the SQLite database
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");

        //Create table in database
        String query = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL, password_hash TEXT NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public static DatabaseHelper getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseHelper();
        }

        return instance;
    }

    private Connection getConnection() {
        return connection;
    }

    // Public methods to perform operations on the SQLite database

    public String getPasswordHashByUser(String user) throws SQLException {
        String query = "SELECT password_hash FROM users WHERE username = ?";
        String passwordHash = "";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user);
        ResultSet results = statement.executeQuery();

        results.close();
        statement.close();
        connection.close();

        if (results.next()) return results.getString("password_hash");
        else return null;
    }


    public void addUser(String username, String email, String passwordHash) throws SQLException {
        String query = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?);";
        //We are using prepared statements
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, passwordHash);
        statement.executeUpdate();

        statement.close();
    }



}
