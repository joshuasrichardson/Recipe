package test;

import Recipe.Recipe;
import Recipe.RecipeBook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RecipeBookTest {

  private RecipeBook recipeBook = new RecipeBook();

  private Recipe recipe;

  private File testFile;

  private Scanner fileInput;

  @Test
  public void addRecipeTest() {
    try {
      testFile = new File("addRecipeTest");
      fileInput = new Scanner(testFile);
      fileInput.nextLine();
      recipe = recipeBook.addRecipe(fileInput);
      assert ("Chocolate Chip Cookies".equals(recipe.getName()));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

}
