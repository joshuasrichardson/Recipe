package DatabaseTest;

import Database.Database;
import Database.IngredientDAO;
import Ingredient.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IngredientDAOTest {
  Database database;
  IngredientDAO ingredientDAO;

  @BeforeEach
  public void connect() {
    database = new Database();
    try {
      ingredientDAO = new IngredientDAO(database.openConnection("jdbc:sqlite:recipeTest.sqlite"));
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
    Ingredient ingredient = new Ingredient("butter", "Kirkland", 4.00, 2.50,
            2.00, 2.25, 1.00, "pounds", "dairy",
            "Walmart", "Provo");
    try {
      ingredientDAO.addIngredientToInformationTable(ingredient);
      Ingredient sameIngredient = ingredientDAO.getIngredientFromTables("butter").get(0);
      assertEquals(ingredient, sameIngredient);
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void addIngredientToInventoryTableTest() {
    Ingredient ingredient = new Ingredient("butter", "Kirkland", "Joshua",
            "fridge",    2.00, 1, "package", 1.00,
            "pound", LocalDate.now(), LocalDate.now());
    Ingredient ingredient2 = new Ingredient("butter", "Kirkland", "Joshua",
            "fridge",    2.00, 1, "package", 1.00,
            "pound", LocalDate.now(), LocalDate.now());
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(ingredient);
    ingredients.add(ingredient2);
    try {
      ingredientDAO.addIngredientToInventoryTable(ingredient);
      ingredientDAO.addIngredientToInventoryTable(ingredient2);
      ArrayList<Ingredient> sameIngredients = ingredientDAO.getIngredientFromTables("butter");
      assertEquals(ingredient, sameIngredients.get(0));
      assertEquals(ingredient2, sameIngredients.get(1));
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

  @Test
  public void selectUsingNameAndBrandTest() {
    Ingredient ingredient = new Ingredient("butter", "Kirkland", 4.00, 2.50,
            2.00, 2.25, 1.00, "pounds", "dairy",
            "Walmart", "Provo");
    try {
      ingredientDAO.addIngredientToInformationTable(ingredient);
      Ingredient sameIngredient = ingredientDAO.selectUsingNameAndBrand("butter", "Kirkland");
      assertEquals(ingredient, sameIngredient);
    } catch (SQLException throwables) {
      fail(throwables.getMessage());
    }
  }

}
