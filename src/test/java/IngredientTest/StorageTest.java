package IngredientTest;

import Ingredient.Ingredient;
import Ingredient.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

  private File testFile;

  private Scanner fileInput;

  private Ingredient ingredient;

  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  @Test
  public void addIngredientToStorageTest() {
    Storage storage = new Storage();
    try {
      testFile = new File("addIngredientTest");
      fileInput = new Scanner(testFile);
      fileInput.nextLine();
      ingredient = storage.addIngredientToStorage(fileInput);
      assertEquals ("vanilla yogurt", ingredient.getName());
      assertEquals (32, ingredient.getAmount());
      assertEquals ("ounces", ingredient.getUnit());
      assertEquals (new Date(2021,2,19), ingredient.getExpirationDate());
      assertEquals (1.84, ingredient.getMostRecentPrice());
      assertEquals ("Walmart", ingredient.getMostRecentStore());
      assertEquals ("Great Value", ingredient.getBrand());
      assertEquals ("dairy", ingredient.getFoodGroup());
      assertEquals ("Provo", ingredient.getCity());
      assertEquals ("dairy", ingredient.getAllergens().get(0));
    }
    catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void addIngredientWrongType() {

  }
}
