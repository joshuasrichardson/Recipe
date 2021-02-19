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
    database.openConnection("jdbc:sqlite:recipeTest.sqlite");
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

  @Test
  public void updateDoubleColumnTest() {
    Storage storage = new Storage();
    ArrayList<String> allergens = new ArrayList<>(List.of("dairy"));
    Date date = new Date(2021, 10, 1);
    Ingredient ingredient = new Ingredient("chocolate", 1.98, 1, "carton",
            1, "gallon", "Walmart", date, "Great Value",
            "Provo", "dairy", allergens);
    assertEquals(-1, ingredient.getAveragePrice());
    try {
      database.updateDoubleColumn("ingredient" , "name", "chocolate milk", "averagePrice", 1.59);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    ingredient = (Ingredient) database.selectFromTable("ingredient", "name", "chocolate milk");
    assertEquals(1.59, ingredient.getAveragePrice());
  }

  @AfterEach
  public void disconnect() {
    database.closeConnection(false);
  }

}
