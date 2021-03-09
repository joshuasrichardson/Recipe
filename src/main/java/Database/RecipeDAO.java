package Database;

import Model.Ingredient;
import Model.Recipe;
import Model.RecipeBook;

import java.sql.*;

public class RecipeDAO {

  final private Connection connection;

  public RecipeDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * adds a recipe to the recipes table in the database.
   *
   * @param recipe the recipe to be inserted to the database.
   * @return whether the recipe successfully uploaded to the database.
   * @throws SQLException if the recipe already exists.
   */
  public boolean addRecipe(Recipe recipe, RecipeBook recipeBook) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipes (name, recipeBookAuthor) values (?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, recipeBook.getAuthor());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
        return true;
      } else {
        return false;
      }
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * inserts a recipe and an ingredient that go together.
   *
   * @param recipe     the recipe that uses the ingredient.
   * @param ingredient the ingredient in the recipe.
   * @return whether it was properly added.
   * @throws SQLException if the recipe and ingredient are already together in the table.
   */
  public boolean addRecipeIngredients(Recipe recipe, Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipeIngredients (recipeName, ingredientName, amount, units) values (?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, ingredient.getName());
      stmt.setDouble(3, ingredient.getAmount());
      stmt.setString(4, ingredient.getUnit());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
        Statement keyStmt = connection.createStatement();
        ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
        keyRS.next();
        int id = keyRS.getInt(1);   // ID of the new recipe

        recipe.setId(id);

        return true;
      } else {
        return false;
      }
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }
}
