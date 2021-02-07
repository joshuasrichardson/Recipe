package test;

import Recipe.Ingredient;
import Recipe.Storage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StorageTest {

  @Test
  public void addIngredientToStorageTest() {
    Storage storage = new Storage();
    try {
      File testFile = new File("addIngredientsToStorageTestFile");
      Scanner fileInput = new Scanner(testFile);
      Ingredient ingredient = storage.addIngredientToStorage(fileInput);
      assert ("chicken".equals(ingredient.getName()));
      assert (ingredient.getAmount() == 1);
      assert ("pound".equals(ingredient.getMeasurement()));
      assert (ingredient.getExpirationDate().equals(new Date(2021,2,18)));
      assert (ingredient.getMostRecentPrice() == 2.05);
      assert ("Costco".equals(ingredient.getMostRecentStore()));

      testFile = new File("addingIngredientsToStorageTestFile2");
      fileInput = new Scanner(testFile);
      ingredient = storage.addIngredientToStorage(fileInput);
      assert ("chicken".equals(ingredient.getName()));
      assertFalse (ingredient.getAmount() == 1);
      assertFalse ("pound".equals(ingredient.getMeasurement()));
      assert (ingredient.getExpirationDate().equals(new Date(2021,2,18)));
      assert (ingredient.getMostRecentPrice() == 2.05);
      assert ("Costco".equals(ingredient.getMostRecentStore()));

      testFile = new File("addIngredientsToStorageTestFile3");
      fileInput = new Scanner(testFile);
      ingredient = storage.addIngredientToStorage(fileInput);
      assert ("chicken".equals(ingredient.getName()));
      assert (ingredient.getAmount() == 1);
      assert ("pound".equals(ingredient.getMeasurement()));
      assert (ingredient.getExpirationDate().equals(new Date(2021,2,18)));
      assertFalse (ingredient.getMostRecentPrice() == 2.05);
      assert ("Costco".equals(ingredient.getMostRecentStore()));

      testFile = new File("addIngredientToStorageTestFile4");
      fileInput = new Scanner(testFile);
      ingredient = storage.addIngredientToStorage(fileInput);
      assert ("chicken".equals(ingredient.getName()));
      assert (ingredient.getAmount() == 1);
      assert ("pound".equals(ingredient.getMeasurement()));
      assert (new Date(2021,2,18)).equals(ingredient.getExpirationDate());
      assert (ingredient.getMostRecentPrice() == 2.05);
      assertFalse ("Costco".equals(ingredient.getMostRecentStore()));

      testFile = new File("addingIngredientToStorageTest5");
      fileInput = new Scanner(testFile);
      ingredient = storage.addIngredientToStorage(fileInput);
      assert ("chicken".equals(ingredient.getName()));
      assert (ingredient.getAmount() == 1);
      assert ("pound".equals(ingredient.getMeasurement()));
      assertNull (ingredient.getExpirationDate());
      assert (ingredient.getMostRecentPrice() == 2.05);
      assert ("Costco".equals(ingredient.getMostRecentStore()));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
