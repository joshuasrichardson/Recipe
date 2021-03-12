package DatabaseTest;

import Database.Database;
import Database.AuthTokenDAO;
import Database.DatabaseAccessException;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static DatabaseTest.DatabaseTest.TEST_CONNECTION_URL;

public class AuthTokenDAOTest {

  Database database;
  AuthTokenDAO authTokenDAO;
  AuthToken authToken;
  AuthToken authToken2;

  @BeforeEach
  public void connect() {
    try {
      database = new Database();
      database.openConnection(TEST_CONNECTION_URL);
      database.clearAllTables();
      database.closeConnection(true);
      authTokenDAO = new AuthTokenDAO(database.getConnection(TEST_CONNECTION_URL));
      authTokenDAO.createAuthTokenTable();
      authToken = new AuthToken("authtoken", "username");
      authToken2 = new AuthToken("authtoken2", "username2");
    } catch (SQLException | DatabaseAccessException throwables) {
      throwables.printStackTrace();
    }
  }

  @AfterEach
  public void disconnect() {
    try {
      database.closeConnection(true);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void addAuthTokenTest() {
    try {
      authTokenDAO.addAuthTokenToTable(authToken);
      authTokenDAO.addAuthTokenToTable(authToken2);
      assertEquals(authToken, authTokenDAO.accessAuthTokenFromTable("authtoken"));
      assertEquals(authToken2, authTokenDAO.accessAuthTokenFromTable("authtoken2"));
    } catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void addAuthTokenTwiceFailTest() {
    try {
      authTokenDAO.addAuthTokenToTable(authToken);
      assertThrows(DatabaseAccessException.class, () -> authTokenDAO.addAuthTokenToTable(authToken));
    } catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void accessInvalidTokenFailTest() {
    assertThrows(DatabaseAccessException.class, () -> authTokenDAO.accessAuthTokenFromTable("authtoken"));
  }

  @Test
  public void clearTableTest() {
    try {
    authTokenDAO.addAuthTokenToTable(authToken);
    authTokenDAO.addAuthTokenToTable(authToken2);
    authTokenDAO.clearTable();
    assertThrows(DatabaseAccessException.class, () -> authTokenDAO.accessAuthTokenFromTable("authtoken"));
    assertThrows(DatabaseAccessException.class, () -> authTokenDAO.accessAuthTokenFromTable("authtoken2"));
  } catch (DatabaseAccessException e) {
    fail(e.getMessage());
  }
  }

}
