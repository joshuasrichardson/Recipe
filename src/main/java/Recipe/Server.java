package Recipe;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

public class Server {

  public static void showOptions() {
    System.out.println("What would you like to do?");
    System.out.println("1. Add a recipe");
    System.out.println("10. Quit");
  }

  public static void main(String[] args) {
    try {
      Scanner input = new Scanner(System.in);

      Database recipeDatabase = new Database();

      RecipeBook recipeBook = new RecipeBook();

      int selection = 0;

      Server.showOptions();
      selection = input.nextInt();
      while (selection != 10) {

        if (selection == 1) {
          recipeBook.addRecipe(input);
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
