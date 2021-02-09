package Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

/**
 * interacts with the user to add and retrieve recipes and ingredients
 */
public class Server {

  /**
   * displays what the user can do with this program
   */
  public static void showOptions() {
    System.out.println("What would you like to do?");
    System.out.println("1. Add a recipe to your recipe book");
    System.out.println("2. Add an ingredient to your inventory");
    System.out.println("10. Quit");
  }

  /**
   * allows the user to go through their recipes and ingredients to help them decide what to make and how to budget
   * @param args
   */
  public static void main(String[] args) {
    try {

      Scanner input = new Scanner(System.in);

      File file = new File("multipleWordRecipeTestFile");

      if (args.length > 0) {
        file = new File(args[0]);
      }

      Database recipeDatabase = new Database();

      RecipeBook recipeBook = new RecipeBook("Megumi"); //Later add functionality to have different authors.

      Storage storage = new Storage();

      int selection;

      recipeDatabase.loadDatabaseDriver();
      recipeDatabase.openConnection("jdbc:sqlite:recipeTest.sqlite");
      recipeDatabase.createTables();

      Server.showOptions();
      selection = input.nextInt();
      while (selection != 10) {

        if (args.length > 0) {
          input = new Scanner(file);
        }

        if (selection == 1) {
          Recipe recipe = recipeBook.addRecipe(input);
          try {
            recipeDatabase.insertRecipe(recipe, recipeBook);
            for (int i = 0; i < recipe.getIngredients().size(); ++i) {
              recipeDatabase.insertRecipeToIngredient(recipe, recipe.getIngredients().get(i));
            }
          } catch (SQLException throwables) {
            throwables.printStackTrace();
          }
        }

        if (selection == 2) {
          Ingredient ingredient = storage.addIngredientToStorage(input);
          recipeDatabase.insertIngredient(ingredient, storage);
        }

        if (args.length > 0) {
          input = new Scanner(System.in);
        }

        Server.showOptions();
        selection = input.nextInt();
      }

      Set<String> names = recipeDatabase.loadRecipes();
      recipeDatabase.closeConnection(true);

      System.out.println(names.toString());

      System.out.println("OK");
    }
    catch (DatabaseException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    catch (FileNotFoundException f) {
      f.printStackTrace();
    }
  }
}
