package Service;

import Database.Database;
import Database.RecipeDAO;
import Model.Recipe;
import Model.RecipeIngredient;
import Result.AddRecipeResult;

import java.sql.SQLException;
import java.util.logging.Level;

import static Server.Server.CONNECTION_URL;
import static Server.Server.logger;

public class AddRecipeService {

  private final String connection;

  private final Database db = new Database();

  public AddRecipeService() {
    this.connection = CONNECTION_URL;
  }

  public AddRecipeService(String connection) {
    this.connection = connection;
  }

  public AddRecipeResult addRecipe(Recipe recipe) {
    logger.log(Level.INFO, "Adding a recipe.");

    try {
      RecipeDAO recipeDAO = new RecipeDAO(db.getConnection(connection));
      recipeDAO.createTables();

      if (alreadyExists(recipe.getName())) {
        //TODO:ask the user to change the name or edit the recipe.
      }

      recipeDAO.addRecipe(recipe);
      for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
        recipeDAO.addRecipeIngredient(recipe.getName(), recipeIngredient);
      }
      for (String appliance : recipe.getAppliances()) {
        recipeDAO.addRecipeAppliance(recipe.getName(), appliance);
      }
      for (String tool : recipe.getTools()) {
        recipeDAO.addRecipeTool(recipe.getName(), tool);
      }

      return new AddRecipeResult(true, "Added " + recipe.getName() + " to the recipe book");
    }
    catch (SQLException throwables) {
      return new AddRecipeResult(false, "Error: " + throwables.getMessage());
    }
    finally {
      try {
        db.closeConnection(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  private boolean alreadyExists(String recipeName) {
    return false;
  }

}
