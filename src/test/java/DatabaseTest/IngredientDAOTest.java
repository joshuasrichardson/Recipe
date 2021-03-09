package DatabaseTest;

import Database.Database;
import Database.IngredientDAO;
import Model.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static test.ServerTest.TEST_CONNECTION_URL;

public class IngredientDAOTest {
  Database database;
  IngredientDAO ingredientDAO;
  Ingredient ingredient;

  @BeforeEach
  public void connect() {
    database = new Database();
    ingredient = new Ingredient("butter", "Joshua", "fridge",
    12.00, 8.00, 11.00,
    11.00, 3.5, 3.5, "pounds", 1,
    "package", "Costco", "Walmart", LocalDate.now(),
            LocalDate.now(), "brand", "Provo", "vegetable",
            new ArrayList<String>(List.of("None")));
    try {
      ingredientDAO = new IngredientDAO(database.getConnection(TEST_CONNECTION_URL));
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
  public void createIngredientInformationTableTest() {
    try {
      ingredientDAO.createIngredientInformationTable();
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void createIngredientInventoryTableTest() {
    try {
      ingredientDAO.createIngredientInventoryTable();
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void addIngredientToInformationTableTest() {
    try {
      ingredientDAO.addIngredientToInformationTable(ingredient);
      Ingredient sameIngredient = ingredientDAO.accessIngredientFromTables("butter", "Joshua").get(0);
      assertEquals(ingredient, sameIngredient);
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void addIngredientToInventoryTableTest() {
    Ingredient ingredient2 = new Ingredient("butter", "Joshua", "fridge",
            12.00, 8.00, 11.00,
            11.00, 3.5, 3.5, "pounds", 1,
            "package", "Costco", "Walmart", LocalDate.now(),
            LocalDate.now(), "brand", "Provo", "vegetable",
            new ArrayList<String>(List.of("None")));
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(ingredient);
    ingredients.add(ingredient2);
    try {
      ingredientDAO.addIngredientToInventoryTable(ingredient);
      ingredientDAO.addIngredientToInventoryTable(ingredient2);
      ArrayList<Ingredient> sameIngredients = ingredientDAO.accessIngredientFromTables("butter", "Joshua");
      assertEquals(ingredient, sameIngredients.get(0));
      assertEquals(ingredient2, sameIngredients.get(1));
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void accessUsingNameAndBrandTest() {
    try {
      ingredientDAO.addIngredientToInformationTable(ingredient);
      Ingredient sameIngredient = ingredientDAO.accessUsingNameAndBrand("butter", "brand");
      assertEquals(ingredient, sameIngredient);
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void removeIngredientTest() {

  }

  @Test
  public void removeIngredientNotExistTest() {

  }

}
