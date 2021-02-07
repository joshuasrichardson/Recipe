package Recipe;

import java.sql.SQLException;
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

      Database recipeDatabase = new Database();

      RecipeBook recipeBook = new RecipeBook();

      Storage storage = new Storage();

      int selection = 0;

      Date i = new Date(2021,2,18);
      Ingredient ing = new Ingredient("sauce");
      ing.setExpirationDate(i);
      System.out.println("date: " + i);

      Server.showOptions();
      selection = input.nextInt();
      while (selection != 10) {

        if (selection == 1) {
          recipeBook.addRecipe(input);
        }

        if (selection == 2) {
          storage.addIngredientToStorage(input);
        }

        Server.showOptions();
        selection = input.nextInt();
      }

      recipeDatabase.loadDatabaseDriver();
      recipeDatabase.openConnection();
      recipeDatabase.createTables();
      //recipeDatabase.insertRecipe(recipe);
      Set<String> names = recipeDatabase.loadRecipes();
      recipeDatabase.closeConnection(true);


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
  }
}
