package DatabaseTest;

import Database.Database;
import Database.DatabaseAccessException;
import Database.UserDAO;
import Model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static DatabaseTest.DatabaseTest.TEST_CONNECTION_URL;

public class UserDAOTest {
  private Database db;
  private User user;
  private UserDAO userDao;

  @BeforeEach
  void setup() {
    user = new User("joe", "irasshaimashe", "joe@gmail.com", "Joe", "San");
    try {
      db = new Database();
      Connection connection;
      connection = db.getConnection(TEST_CONNECTION_URL);
      db.clearAllTables();
      userDao = new UserDAO(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void tearDown() {
    try {
      db.closeConnection(true);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void createTableTest() {
    try {
      userDao.createUserTable();
    } catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void addUserTest() {
    try {
      userDao.addUserToTable(user);
      User compareTest = userDao.accessUserFromTable("username", user.getUsername(),
              "password", user.getPassword());
      assertNotNull(compareTest);
      assertEquals(user, compareTest);
    } catch (DatabaseAccessException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  @Test
  public void addUserTestFail() {
    try {
      userDao.addUserToTable(user);
      User compareTest = userDao.accessUserFromTable("username", user.getUsername(),
              "password", user.getPassword());
      assertNotNull(compareTest);
      assertEquals(user, compareTest);
      assertThrows(DatabaseAccessException.class, () -> {
        userDao.addUserToTable(user);
      }, "Cannot add to table with same id twice.");
    } catch (DatabaseAccessException e) {
      e.printStackTrace();
      fail(e.getMessage());//the first insert failed
    }
  }

  @Test
  public void accessUserFromTableTest() {
    try {
      userDao.addUserToTable(user);
      assertEquals(user, userDao.accessUserFromTable("username", "joe"));
    }
    catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void accessUserFromTableFailTest() {
    assertThrows(DatabaseAccessException.class, () -> {
      userDao.accessUserFromTable("username", "joe");
    });
  }

  @Test
  public void accessTest() {
    try {
      userDao.addUserToTable(user);
      User compareTest = userDao.accessUserFromTable("username", user.getUsername(),
              "password", user.getPassword());
      User notSame = new User();
      assertNotNull(compareTest);
      assertEquals(user, compareTest);
      assertNotEquals(notSame, user);
    } catch (DatabaseAccessException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  @Test
  public void accessTestFail() {
    try {
      userDao.addUserToTable(user);
      User notSame = new User();
      assertThrows(DatabaseAccessException.class, () -> {
        userDao.accessUserFromTable("username", notSame.getUsername(),
                "password", notSame.getPassword());
      });
      assertNotEquals(notSame, user);
    } catch (DatabaseAccessException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  @Test
  public void clearTest() {
    try {
      userDao.addUserToTable(user);
      User compareTest = userDao.accessUserFromTable("username", user.getUsername(),
              "password", user.getPassword());
      assertNotNull(compareTest);
      assertEquals(user, compareTest);
      userDao.clearTable();
      assertThrows(DatabaseAccessException.class, () -> {
        userDao.accessUserFromTable("username", user.getUsername(),
                "password", user.getPassword());
      });
    } catch (DatabaseAccessException e) {
      fail(e.getMessage());
    }
  }


}
