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
   * @throws DatabaseAccessException
   */
  public boolean createUserTable() throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "CREATE TABLE IF NOT EXISTS User (\n" +
              "username varchar(255) not null primary key, \n" +
              "password varchar(255) not null, \n" +
              "email varchar(255) not null, \n" +
              "firstName varchar(255) not null, \n" +
              "lastName varchar(255) not null);";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseAccessException("SQL Error encountered while creating User table");
    }
    return true;
  }

  /**
   * uses the information provided by the user to add them to the table.
   * @param user the user to add to the table.
   * @return whether the user was added to the table.
   * @throws DatabaseAccessException
   */
  public boolean addUserToTable(User user) throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "INSERT INTO User (username, password, email, firstName, lastName) " +
              "values (?, ?, ?, ?, ?)";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.execute();
    } catch (SQLException e) {
      throw new DatabaseAccessException("User already exists.");
    }
    return true;
  }

  /**
   * accesses a user from the database using only their username.
   * @param username the username of the person to access.
   * @return the User.
   * @throws DatabaseAccessException
   */
  public User accessUserFromTable(String username) throws DatabaseAccessException {
    User user = new User();
    PreparedStatement stmt;
    try {
      String sql = "SELECT * FROM User WHERE username = '" + username + "';";
      stmt = connection.prepareStatement(sql);
      ResultSet keyRS = stmt.executeQuery();
      keyRS.next();
      user.setUsername(keyRS.getString(1));
      user.setPassword(keyRS.getString(2));
      user.setEmail(keyRS.getString(3));
      user.setFirstName(keyRS.getString(4));
      user.setLastName(keyRS.getString(5));
    } catch (SQLException e) {
      throw new DatabaseAccessException("The user doesn't exist.");
    }
    return user;
  }

  /**
   * gets a user from the table using two columns.
   * @param column column with information to check.
   * @param value value of the column.
   * @return the user to login or something.
   * @throws DatabaseAccessException
   */
  public User accessUserFromTable(String column, String value) throws DatabaseAccessException {
    User user = new User();
    PreparedStatement stmt;
    try {
      String sql = "SELECT * FROM User WHERE " + column + " = '" + value + "';";
      stmt = connection.prepareStatement(sql);
      ResultSet keyRS = stmt.executeQuery();
      keyRS.next();
      user.setUsername(keyRS.getString(1));
      user.setPassword(keyRS.getString(2));
      user.setEmail(keyRS.getString(3));
      user.setFirstName(keyRS.getString(4));
      user.setLastName(keyRS.getString(5));
    } catch (SQLException e) {
      throw new DatabaseAccessException("The user doesn't exist.");
    }
    return user;
  }

  /**
   * gets a user from the table using two columns.
   * @param column1 first column with information to check.
   * @param value1 value of the first column.
   * @param column2 second column with information to check.
   * @param value2 value of the second column.
   * @return the user to login or something.
   * @throws DatabaseAccessException
   */
  public User accessUserFromTable(String column1, String value1, String column2, String value2) throws DatabaseAccessException {
    User user = new User();
    PreparedStatement stmt;
    try {
      String sql = "SELECT * FROM User WHERE " + column1 + " = '" + value1 + "'\n" +
              "\tAND " + column2 + " = '" + value2 + "';";
      stmt = connection.prepareStatement(sql);
      ResultSet keyRS = stmt.executeQuery();
      keyRS.next();
      user.setUsername(keyRS.getString(1));
      user.setPassword(keyRS.getString(2));
      user.setEmail(keyRS.getString(3));
      user.setFirstName(keyRS.getString(4));
      user.setLastName(keyRS.getString(5));
    } catch (SQLException e) {
      throw new DatabaseAccessException("The user doesn't exist, or the password is incorrect.");
    }
    return user;
  }

  /**
   * clears the User table in the database.
   * @throws DatabaseAccessException if the User table doesn't exist etc.
   */
  public void clearTable() throws DatabaseAccessException
  {
    PreparedStatement stmt = null;
    try {
      String sql = "DELETE FROM User;";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseAccessException("SQL Error encountered while clearing the user table");
    }
  }

}
