package test;

import Recipe.Ingredient;
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
      testFile = new File("multipleWordRecipeTestFile");
      fileInput = new Scanner(testFile);
      recipe = recipeBook.addRecipe(fileInput);
      assert ("Grandma's Famous Cookies".equals(recipe.getName()));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

}
