package Database;


import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * a class to get and set users in the database.
 */
public class UserDAO {

  final private Connection connection;

  /**
   * creates a connection to the user table and updates and retrieves information.
   * @param connection the connection to the database.
   */
  public UserDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * creates a table that stores information about the user if it doesn't already exist.
   * @return whether a table was created for the current user.
   * @throws SQLException
   */
  public boolean createUserTable() throws SQLException {
    PreparedStatement stmt = null;
    String sql = "CREATE TABLE IF NOT EXISTS User (\n" +
            "person_id varchar(255) not null primary key, \n" +
            "username varchar(255) not null, \n" +
            "password varchar(255) not null, \n" +
            "email varchar(255) not null, \n" +
            "first_name varchar(255) not null, \n" +
            "last_name varchar(255) not null);";
    stmt = connection.prepareStatement(sql);
    stmt.execute();
    return true;
  }

  public void createAuthTokensTable() throws SQLException {
    Database db = new Database(this.connection);

    db.createTable("AuthTokens", "authToken", "varchar(255)",
            "userID varchar(255)");
  }

  /**
   * uses the information provided by the user to add them to the table.
   * @param user the user to add to the table.
   * @return whether the user was added to the table.
   * @throws SQLException
   */
  public boolean addUserToTable(User user) throws SQLException {
    PreparedStatement stmt = null;
      String sql = "INSERT INTO User (person_id, username, password, email, first_name, last_name) " +
              "values (?, ?, ?, ?, ?, ?)";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, user.getUserID());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getPassword());
      stmt.setString(4, user.getEmail());
      stmt.setString(5, user.getFirstName());
      stmt.setString(6, user.getLastName());
      stmt.execute();
    return true;
  }

  public boolean addAuthTokenToTable(String authToken, String userID) throws SQLException {
    PreparedStatement stmt = null;
    String sql = "INSERT INTO AuthTokens (authToken, userID) " +
            "values (?, ?)";
    stmt = connection.prepareStatement(sql);
    stmt.setString(1, authToken);
    stmt.setString(2, userID);
    stmt.execute();
    return true;
  }

  /**
   * gets a user object from the database table.
   * @param userID the way to identify the user.
   * @return whether a new table was created.
   * @throws SQLException
   */
  public User accessUserFromTable(String userID) throws SQLException {
    User user = new User();
    PreparedStatement stmt;
    String sql = "SELECT * FROM User WHERE person_id = \'" + userID + "\';";
    stmt = connection.prepareStatement(sql);
    ResultSet keyRS = stmt.executeQuery();
    keyRS.next();
    user.setUserID(keyRS.getString(1));
    user.setUsername(keyRS.getString(2));
    user.setPassword(keyRS.getString(3));
    user.setEmail(keyRS.getString(4));
    user.setFirstName(keyRS.getString(5));
    user.setLastName(keyRS.getString(6));
    return user;
  }

  /**
   * clears the User table in the database.
   * @throws SQLException if the User table doesn't exist etc.
   */
  public void clearTable() throws SQLException {
    PreparedStatement stmt = null;
    String sql = "DELETE FROM User;";
    stmt = connection.prepareStatement(sql);
    stmt.execute();
  }

}
