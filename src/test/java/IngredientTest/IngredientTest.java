package IngredientTest;

import Ingredient.Ingredient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

  @Test
  public void IngredientConstructorTest() {

    ArrayList<String> allergens = new ArrayList<>(List.of("dairy"));
    LocalDate date = LocalDate.of(2021, 10, 1);

    Ingredient milk = new Ingredient("milk", 1.98, 1, "gallon",
            1, "carton", "Walmart", date, "Great Value",
            "Provo", "dairy", allergens);

    assertAll("milk",
            () -> assertEquals ("milk", milk.getName()),
            () -> assertEquals (1.98, milk.getMostRecentPrice()),
            () -> assertEquals (1, milk.getAmount()),
            () -> assertEquals ("gallon", milk.getUnit()),
            () -> assertEquals (1, milk.getNumber()),
            () -> assertEquals ("carton", milk.getContainer()),
            () -> assertEquals ("Walmart", milk.getMostRecentStore()),
            () -> assertEquals (date, milk.getExpirationDate()),
            () -> assertEquals ("Great Value", milk.getBrand()),
            () -> assertEquals ("Provo", milk.getCity()),
            () -> assertEquals ("dairy", milk.getFoodGroup()),
            () -> assertEquals ("dairy", milk.getAllergens().get(0))
    );
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
