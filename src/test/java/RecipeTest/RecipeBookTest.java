package RecipeTest;

import Recipe.Recipe;
import Recipe.RecipeBook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
      assertEquals ("Chocolate Chip Cookies", recipe.getName());

    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

  }

}
