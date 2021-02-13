package test;

import Database.Database;
import Database.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class DatabaseTest {

  Database database = new Database();

  @BeforeEach
  public void connect() {
    try {
      database.openConnection("jdbc:sqlite:recipeTest.sqlite");
    } catch (DatabaseException d) {
      d.printStackTrace();
    }
  }

  @Test
  public void createTableTest() {
    try {
      database.createTable("superHeros", "id", "integer",
              "power varchar(255)", "rating integer", "hometown varchar(255)");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @AfterEach
  public void disconnect() {
    try {
      database.closeConnection(true);
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
  }

}
