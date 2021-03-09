package Main;

import Database.*;
import Model.Ingredient;
import Model.Storage;
import Model.Recipe;
import Model.RecipeBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

public class Main {
  /**
   * allows the user to go through their recipes and ingredients to help them decide what to make and how to budget
   * @param args the database to save to (required) and the file to read in (optional)
   */
  public static void main(String[] args) {
    try {
      String portNumber = null;
      if (args.length < 1) {
        throw new IllegalArgumentException("No argument provided");
      }
      else if (args.length == 1) {
        portNumber = args[0];
        new Server().run(portNumber);
      }
    }
    catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  private static boolean isPort(String argument) {
    for (char c : argument.toCharArray()) {
      if (!Character.isDigit(c)) return false;
    }
    return true;
  }

  private static void runFromIntellij(String[] args) {
    /*String userName;
    userName = args[0];

    try {
      Scanner input = new Scanner(System.in);

      File file = new File("addIngredientTest");

      if (args.length > 1) {
        file = new File(args[1]);
      }

      Database recipeDatabase = new Database();

      RecipeBook recipeBook = new RecipeBook();

      Storage storage = new Storage();

      recipeBook.setAuthor(userName);

      storage.setName(userName);

      int selection;

      recipeDatabase.loadDatabaseDriver();
      recipeDatabase.openConnection("jdbc:sqlite:" + args[0] + ".sqlite");
      recipeDatabase.createTables();

      IngredientDAO ingredientDAO = new IngredientDAO(recipeDatabase.getConnection("jdbc:sqlite:" + args[0] + ".sqlite"));

      if (args.length > 1) {
        input = new Scanner(file);
      }

      showOptions();
      selection = input.nextInt();
      input.nextLine();
      while (selection != 10) {

        if (selection == 1) {
          Recipe recipe = recipeBook.addRecipe(input);
          try {
            recipeDatabase.insertRecipe(recipe, recipeBook);
            for (int i = 0; i < recipe.getIngredients().size(); ++i) {
              recipeDatabase.insertRecipeIngredients(recipe, recipe.getIngredients().get(i));
            }
          } catch (SQLException throwables) {
            throwables.printStackTrace();
          }
        }

        if (selection == 2) {
          Ingredient ingredient = storage.addIngredientToStorage(input);
          //FIXME::ingredientDAO.addIngredientToTable(ingredient, storage);
        }

        if (selection == 9) {
          recipeDatabase.addColumnToTable(input);
        }

        showOptions();
        selection = input.nextInt();
      }

      Set<String> names = recipeDatabase.loadRecipes();
      recipeDatabase.closeConnection(true);

      System.out.println(names.toString());

      System.out.println("OK");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (FileNotFoundException f) {
      f.printStackTrace();
    }*/
  }

  /**
   * finds a name out of the a url to assign as the author of the recipe book
   * and owner of the storage container.
   */
  public static String findUsername(String argument) {
    return argument.substring(argument.lastIndexOf(':') + 1, argument.indexOf('.'));
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


}
