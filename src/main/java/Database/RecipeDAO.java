package Database;

import Model.RecipeIngredient;
import Model.Recipe;

import java.sql.*;
import java.util.ArrayList;

public class RecipeDAO {

  final private Connection connection;
  private final Database db;

  public RecipeDAO(Connection connection) {
    this.connection = connection;
    db = new Database(connection);
  }

  public void createTables() throws SQLException {
    createRecipeTable();
    createRecipeIngredientsTable();
    createRecipeApplianceTable();
    createRecipeToolTable();
  }

  /**
   * creates a table to store recipes.
   * @throws SQLException
   */
  public void createRecipeTable() throws SQLException {
    db.createTable("Recipe", "name", "varchar(255)",
            "owner varchar(255)", "servings integer", "description varchar(255)",
            "instructions varchar(255)", "minutes integer", "temperature integer", "calories integer");
  }

  /**
   * creates a table to store recipes' ingredients.
   * @throws SQLException
   */
  public void createRecipeIngredientsTable() throws SQLException {
    db.createTable("RecipeIngredients", "id", "integer",
            "recipeName varchar(255)", "ingredient varchar(255)", "amount double", "units varchar(255)");
  }

  /**
   * creates a table to store the recipes' appliances.
   * @throws SQLException
   */
  public void createRecipeApplianceTable() throws SQLException {
    db.createTable("RecipeAppliance", "id", "integer",
            "recipeName varchar(255)", "appliance varchar(255)");
  }

  /**
   * creates a table to store the recipes' tools.
   * @throws SQLException
   */
  public void createRecipeToolTable() throws SQLException {
    db.createTable("RecipeTool", "id", "integer",
            "recipeName varchar(255)", "tool varchar(255)");
  }

  /**
   * adds a recipe to the recipes table in the database.
   *
   * @param recipe the recipe to be inserted to the database.
   * @throws SQLException if the recipe already exists.
   */
  public void addRecipe(Recipe recipe) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into Recipe (name, owner, servings, description, instructions, minutes, " +
              "temperature, calories) values (?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, recipe.getOwner());
      stmt.setInt(3, recipe.getServings());
      stmt.setString(4, recipe.getDescription());
      stmt.setString(5, recipe.getInstructions());
      stmt.setInt(6, recipe.getMinutes());
      stmt.setInt(7, recipe.getTemperature());
      stmt.setInt(8, recipe.getCalories());

      stmt.executeUpdate();
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public Recipe accessRecipe(String recipeName) {
    Recipe recipe = null;
    try {
      ResultSet resultSet = db.accessFromTable("Recipe", "name", recipeName);
      recipe = setRecipes(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return recipe;
  }

  private Recipe setRecipes(ResultSet resultSet) throws SQLException {
    Recipe recipe = null;
    while (resultSet.next()) {
      recipe = new Recipe(resultSet.getString(1));
      recipe.setOwner(resultSet.getString(2));
      recipe.setServings(resultSet.getInt(3));
      recipe.setDescription(resultSet.getString(4));
      recipe.setInstructions(resultSet.getString(5));
      recipe.setMinutes(resultSet.getInt(6));
      recipe.setTemperature(resultSet.getInt(7));
      recipe.setCalories(resultSet.getInt(8));
    }
    return recipe;
  }

  /**
   * inserts a recipe and an ingredient that go together.
   *
   * @param recipeName the recipe the ingredient goes with.
   * @param recipeIngredient the ingredient, amount, and unit of the ingredient.
   * @throws SQLException if the recipe and ingredient are already together in the table.
   */
  public void addRecipeIngredient(String recipeName, RecipeIngredient recipeIngredient) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into RecipeIngredients (recipeName, ingredient, amount, units) values (?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipeName);
      stmt.setString(2, recipeIngredient.getIngredient());
      stmt.setDouble(3, recipeIngredient.getAmount());
      stmt.setString(4, recipeIngredient.getUnit());

      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public ArrayList<RecipeIngredient> accessRecipeIngredients(String recipeName) {
    ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    try {
      ResultSet resultSet = db.accessFromTable("RecipeIngredients", "recipeName", recipeName);
      recipeIngredients = setRecipeIngredients(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return recipeIngredients;
  }

  private ArrayList<RecipeIngredient> setRecipeIngredients(ResultSet resultSet) throws SQLException {
    ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    while (resultSet.next()) {
      RecipeIngredient recipe = new RecipeIngredient();
      recipe.setIngredient(resultSet.getString(3));
      recipe.setAmount(resultSet.getDouble(4));
      recipe.setUnit(resultSet.getString(5));
      recipeIngredients.add(recipe);
    }
    return recipeIngredients;
  }

  public void addRecipeTool(String recipeName, String recipeTool) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into RecipeTool (recipeName, tool) values (?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipeName);
      stmt.setString(2, recipeTool);

      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public ArrayList<String> accessRecipeTools(String recipeName) {
    ArrayList<String> recipeTools = new ArrayList<>();
    try {
      ResultSet resultSet = db.accessFromTable("RecipeTool", "recipeName", recipeName);
      recipeTools = setRecipeTools(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return recipeTools;
  }

  private ArrayList<String> setRecipeTools(ResultSet resultSet) throws SQLException {
    ArrayList<String> recipeTools = new ArrayList<>();
    while (resultSet.next()) {
      recipeTools.add(resultSet.getString(3));
    }
    return recipeTools;
  }

  public void addRecipeAppliance(String recipeName, String recipeAppliance) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into RecipeAppliance (recipeName, appliance) values (?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipeName);
      stmt.setString(2, recipeAppliance);

      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public ArrayList<String> accessRecipeAppliances(String recipeName) {
    ArrayList<String> recipeAppliances = new ArrayList<>();
    try {
      ResultSet resultSet = db.accessFromTable("RecipeAppliance", "recipeName", recipeName);
      recipeAppliances = setRecipeAppliances(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return recipeAppliances;
  }

  private ArrayList<String> setRecipeAppliances(ResultSet resultSet) throws SQLException {
    ArrayList<String> recipeAppliances = new ArrayList<>();
    while (resultSet.next()) {
      recipeAppliances.add(resultSet.getString(3));
    }
    return recipeAppliances;
  }

}
