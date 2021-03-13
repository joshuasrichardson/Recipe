package Service;

import Database.Database;
import Database.RecipeDAO;
import Model.Recipe;
import Result.GetRecipeResult;

import java.sql.SQLException;

import static Server.Server.CONNECTION_URL;

public class GetRecipeService {

  private final String connection;

  private final Database db = new Database();

  public GetRecipeService() {
    this.connection = CONNECTION_URL;
  }

  public GetRecipeService(String connection) {
    this.connection = connection;
  }

  public GetRecipeResult getRecipe(String recipeName) {
    try {
      RecipeDAO recipeDAO = new RecipeDAO(db.getConnection(connection));

      Recipe recipe = recipeDAO.accessRecipe(recipeName);

      //TODO: if the recipe doesn't exist, search for similar recipes and ask the user if that's what they want.

      recipe.setIngredients(recipeDAO.accessRecipeIngredients(recipeName));
      recipe.setAppliances(recipeDAO.accessRecipeAppliances(recipeName));
      recipe.setTools(recipeDAO.accessRecipeTools(recipeName));

      return new GetRecipeResult(true, recipe);
    }
    catch (SQLException throwables) {
      return new GetRecipeResult(false, "Error: " + throwables.getMessage());
    }
    finally {
      try {
        db.closeConnection(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
