package Main;

import Database.*;
import Ingredient.*;
import Recipe.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

/**
 * interacts with the user to add and retrieve recipes and ingredients
 */
public class Server {

  /**
   * finds a name out of the first commandline argument to assign as the author of the recipe book
   * and owner of the storage container.
   */
  public static String findUsername(String arg0) {
    return arg0.substring(arg0.lastIndexOf(':') + 1, arg0.indexOf('.'));
  }

  /**
   * displays what the user can do with this program
   */
  public static void showOptions() {
    System.out.println("What would you like to do?");
    System.out.println("1. Add a recipe to your recipe book");
    System.out.println("2. Add an ingredient to your inventory");
    System.out.println("9. Add a new column to a table");
    System.out.println("10. Quit");
  }

  /**
   * allows the user to go through their recipes and ingredients to help them decide what to make and how to budget
   * @param args the database to save to (required) and the file to read in (optional)
   */
  public static void main(String[] args) {
    try {

      String user = "Default";

      if (args.length == 0) {
        throw new ArgumentException("Add a database to the input arguments.");
      }
      else {
        user = findUsername(args[0]);
      }

      Scanner input = new Scanner(System.in);

      File file = new File("addIngredientTest");

      if (args.length > 1) {
        file = new File(args[1]);
      }

      Database recipeDatabase = new Database();

      RecipeBook recipeBook = new RecipeBook();

      Storage storage = new Storage();

      recipeBook.setAuthor(user);

      storage.setName(user);

      int selection;

      recipeDatabase.loadDatabaseDriver();
      recipeDatabase.openConnection(args[0]);
      recipeDatabase.createTables();

      if (args.length > 1) {
        input = new Scanner(file);
      }

      Server.showOptions();
      selection = input.nextInt();
      input.nextLine();
      while (selection != 10) {

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

        if (selection == 9) {
          recipeDatabase.addColumnToTable(input);
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
    catch (ArgumentException dbProblem) {
      dbProblem.getMessage();
    }
  }
}
