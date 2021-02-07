package test;

import Recipe.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.Date;

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

    //System.out.println(milk.toString());
  }

}
