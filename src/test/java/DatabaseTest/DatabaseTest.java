package DatabaseTest;

import Database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static test.ServerTest.TEST_CONNECTION_URL;

public class DatabaseTest {

  Database database;

  @BeforeEach
  public void connect() {
    database = new Database();
    try {
      database.openConnection(TEST_CONNECTION_URL);
      database.createTables();
      database.clearAllTables();
      database.createTable("superHeroes", "id", "integer",
              "power varchar(255)", "rating integer", "hometown varchar(255)");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @AfterEach
  public void disconnect() {
    try {
      database.closeConnection(false);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void addColumnTest() {
    try {
      database.addColumnToTable("superHeroes", "weakness", "varchar(255)");
      database.accessFromTable("superHeroes", "weakness", "nothing");
    }
    catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void addExistingColumnFailTest() {
    assertThrows(SQLException.class, () -> {
      database.addColumnToTable("superHeroes", "power", "varchar(255)");
    });
  }

}
