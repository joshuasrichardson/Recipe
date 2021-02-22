package DatabaseTest;

import Database.Database;
import Ingredient.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

  Database database;

  @BeforeEach
  public void connect() {
    database = new Database();
    try {
      database.openConnection("jdbc:sqlite:recipeTest.sqlite");
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
  public void createTableTest() {
    try {
      database.createTable("superHeros", "id", "integer",
              "power varchar(255)", "rating integer", "hometown varchar(255)");
    } catch (SQLException e) {
      fail(e.getMessage());
    }
  }

  //FIXME:Make this test work
  @Test
  public void updateDoubleColumnTest() {
    /*Storage storage = new Storage();
    ArrayList<String> allergens = new ArrayList<>(List.of("dairy"));
    Date date = new Date(2021, 10, 1);
    Ingredient ingredient = new Ingredient("chocolate", 1.98, 1, "carton",
            1, "gallon", "Walmart", date, "Great Value",
            "Provo", "dairy", allergens);
    assertEquals(-1, ingredient.getAveragePricePerUnit());
    try {
      database.updateDoubleColumn("ingredient" , "name", "chocolate milk", "averagePrice", 1.59);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    //ingredient = (Ingredient) database.selectFromTable("ingredient", "name", "chocolate milk");
    //assertEquals(1.59, ingredient.getAveragePrice());*/
  }

  /*
  @Test
  public void findTableTest() {
    String table = new Database().findTable("table,column,value");
    assertEquals("table", table);
  }

  @Test
  public void findColumnTest() {
    String column = new Database().findColumn("table,column,value");
    assertEquals("column", column);
  }

  @Test
  public void findValueTest() {
    String value = new Database().findValue("table,column,value");
    assertEquals("value", value);
  }
   */

}
