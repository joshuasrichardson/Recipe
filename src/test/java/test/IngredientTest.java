package test;

import Ingredient.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;

public class IngredientTest {

  @Test
  public void IngredientConstructorTest() {
    String name = "milk";
    double averagePrice = 2.00;
    double salePrice = 1.50;
    double mostRecentPrice = 1.98;
    double amount = 1;
    String measurement = "gallon";
    String cheapestStore = "Walmart";
    String mostRecentStore = "Walmart";
    Date expirationDate = new Date(2021, 2, 18);

    Ingredient milk = new Ingredient(name, averagePrice, salePrice, mostRecentPrice, amount,
            measurement, cheapestStore, mostRecentStore, expirationDate);

    assert (milk.getName() =="milk");
    assert (milk.getAveragePrice() == 2.00);
    assert (milk.getSalePrice() == 1.50);
    assert (milk.getMostRecentPrice() == 1.98);
    assert (milk.getAmount() == 1);
    assert (milk.getMeasurement() == "gallon");
    assert (milk.getCheapestStore() == "Walmart");
    assert (milk.getMostRecentStore() == "Walmart");
    assert (milk.getExpirationDate() == expirationDate);

    System.out.println(milk.toString());
  }

  @Test
  public void findIngredientNameTest() {
    String name = Ingredient.findIngredientName("yummy food 106.1 cartons");
    assert (name.equals("yummy food"));
  }

  @Test
  public void findIngredientNumberTest() {
    double number = Ingredient.findIngredientNumber("yummy food 15.7 cartons");
    assert (number == 15.7);
  }

  @Test
  public void findIngredientUnitTest() {
    String name = Ingredient.findIngredientUnit("yummy food 1.7 cartons");
    assert (name.equals("cartons"));
  }

  @Test
  public void findNoIngredientUnitTest() {
    String name = Ingredient.findIngredientUnit("yummy food 100.3");
    assert (name.length() == 0);
  }

}
