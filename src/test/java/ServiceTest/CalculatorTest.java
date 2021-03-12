package ServiceTest;

import Database.*;
import Model.Ingredient;
import Request.AddIngredientRequest;
import Service.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static DatabaseTest.DatabaseTest.TEST_CONNECTION_URL;

public class CalculatorTest {

  Database db = new Database();
  IngredientDAO ingredientDAO;
  Calculator calc = new Calculator(TEST_CONNECTION_URL);

  @BeforeEach
  public void setup() {
    try {
      db.openConnection(TEST_CONNECTION_URL);
      db.clearAllTables();
      db.closeConnection(true);
      ingredientDAO = new IngredientDAO(db.getConnection(TEST_CONNECTION_URL));
      Ingredient ingredient = new Ingredient();
      ingredient.setName("newIng");
      ingredient.setTotalAmountBought(10);
      ingredient.setAveragePricePerUnit(2);
      ingredient.setUnit("pounds");
      ingredient.setCheapestStore("Smith's");
      ingredientDAO.addIngredientToInformationTable(ingredient);
      db.closeConnection(true);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Test
  public void getAveragePricePerUnitTest() {
    AddIngredientRequest request = new AddIngredientRequest();
    request.setIngredientName("newIng");
    request.setAmount(10);
    request.setNumber(1);
    request.setUnit("pounds");
    request.setMostRecentPrice(10);
    assertEquals(1.5, calc.calculateAveragePrice(request));
  }

  @Test
  public void getAveragePricePerUnitNewItemTest() {
    AddIngredientRequest request = new AddIngredientRequest();
    request.setIngredientName("notInDatabase");
    request.setAmount(10);
    request.setNumber(1);
    request.setUnit("pounds");
    request.setMostRecentPrice(10);
    assertEquals(1, calc.calculateAveragePrice(request));
  }

  @Test
  public void getTotalAmountTest0() {
    AddIngredientRequest request = new AddIngredientRequest();
    request.setIngredientName("notInDatabase");
    request.setAmount(10);
    request.setNumber(1);
    request.setUnit("pounds");
    request.setMostRecentPrice(10);
    assertEquals(10, calc.calculateTotalAmount(request));
  }

  @Test
  public void getTotalAmountTest1() {
    AddIngredientRequest request = new AddIngredientRequest();
    request.setIngredientName("newIng");
    request.setAmount(10);
    request.setNumber(1);
    request.setUnit("pounds");
    request.setMostRecentPrice(10);
    assertEquals(20, calc.calculateTotalAmount(request));
  }

  @Test
  public void getTotalAmountTest2() {
    AddIngredientRequest request = new AddIngredientRequest();
    request.setIngredientName("newIng");
    request.setAmount(10);
    request.setNumber(2);
    request.setUnit("pounds");
    request.setMostRecentPrice(10);
    assertEquals(30, calc.calculateTotalAmount(request));
  }
}
