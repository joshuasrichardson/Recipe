package Database;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * a class to get and set authorization tokens in the database.
 */
public class AuthTokenDAO {

  private final Connection connection;

  /**
   * creates an authToken for a login.
   * @param connection the connection to the database.
   */
  public AuthTokenDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * Makes a table for an authToken if it doesn't already exist.
   * @return whether a table was successfully created.
   * @throws DatabaseAccessException
   */
  public boolean createAuthTokenTable() throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "CREATE TABLE IF NOT EXISTS AuthToken (\n" +
              "authtoken varchar(255) not null primary key, \n" +
              "username varchar(255) not null);";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseAccessException("SQL Error encountered while creating AuthToken table");
    }
    return true;
  }

  /**
   * uses the information provided to add a person to the table.
   * @param authToken the authToken to add
   * @return whether the person was added to the table.
   * @throws DatabaseAccessException if the table doesn't exist etc.
   */
  public boolean addAuthTokenToTable(AuthToken authToken) throws DatabaseAccessException {
    PreparedStatement stmt = null;
    try {
      String sql = "INSERT INTO AuthToken (authtoken, username) VALUES (?, ?)";
      stmt = connection.prepareStatement(sql);
      stmt.setString(1, authToken.getAuthTokenID());
      stmt.setString(2, authToken.getUsername());
      stmt.execute();
    } catch (SQLException e) {
      throw new DatabaseAccessException("SQL Error encountered while inserting into AuthToken table");
    }
    return true;
  }

  /**
   * gets an authToken object from the database table.
   * @param authTokenID to know which user to make and authToken for.
   * @return the authorization token taken from the table.
   * @throws DatabaseAccessException if the table or ID doesn't exist.
   */
  public AuthToken accessAuthTokenFromTable(String authTokenID) throws DatabaseAccessException {
    AuthToken authToken = new AuthToken();
    PreparedStatement stmt;
    try {
      String sql = "SELECT * FROM AuthToken WHERE authtoken = '" + authTokenID + "';";
      stmt = connection.prepareStatement(sql);
      ResultSet keyRS = stmt.executeQuery();
      keyRS.next();
      authToken.setAuthTokenID(keyRS.getString(1));
      authToken.setUsername(keyRS.getString(2));
    } catch (SQLException e) {
      throw new DatabaseAccessException("Invalid authtoken");
    }
    return authToken;
  }

  /**
   * clears the AuthToken table in the database
   * @throws DatabaseAccessException if the table doesn't exist
   */
  public void clearTable() throws DatabaseAccessException
  {
    PreparedStatement stmt = null;
    try {
      String sql = "DELETE FROM AuthToken;";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseAccessException("SQL Error encountered while clearing the authToken table");
    }
  }
}

