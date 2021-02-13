package test;

import Ingredient.Ingredient;
import Ingredient.Storage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

  private File testFile;

  private Scanner fileInput;

  private Ingredient ingredient;

  @Test
  public void addIngredientToStorageTest() {
    Storage storage = new Storage();
    try {
      testFile = new File("addIngredientTest");
      fileInput = new Scanner(testFile);
      fileInput.nextLine();
      ingredient = storage.addIngredientToStorage(fileInput);
      assertEquals ("vanilla yogurt", ingredient.getName());
      assertEquals (ingredient.getAmount(), 32);
      assertEquals ("ounces", ingredient.getMeasurement());
      assertEquals (ingredient.getExpirationDate(),new Date(2021,2,19));
      assertEquals (ingredient.getMostRecentPrice(), 1.84);
      assertEquals ("Walmart", ingredient.getMostRecentStore());
      assertEquals("Great Value", ingredient.getBrand());
      assertEquals("dairy", ingredient.getFoodGroup());
      assertEquals("Provo", ingredient.getCity());
      assertEquals("dairy", ingredient.getAllergens().get(0));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void addIngredientWrongType() {

  }

}
