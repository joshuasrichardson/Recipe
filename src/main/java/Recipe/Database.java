package Recipe;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * deals with adding and removing things from the database to store information
 */
public class Database {

  private Connection connection;

  /**
   * loads the database driver
   *
    * @throws ClassNotFoundException
   */
  public void loadDatabaseDriver() throws ClassNotFoundException {

    // Name of class that implements database driver
    final String driver = "org.sqlite.JDBC";

    // Dynamically load the driver class
    Class.forName(driver);
  }

  /**
   * connects to the database
   *
   * @throws DatabaseException
   */
  public void openConnection() throws DatabaseException {
    try {
      final String CONNECTION_URL = "jdbc:sqlite:recipes.sqlite";

      // Open a database connection
      connection = DriverManager.getConnection(CONNECTION_URL);

      // Start a transaction
      connection.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new DatabaseException("openConnection failed");
    }
  }

  /**
   * cuts off the connection to the database
   *
   * @param commit
   * @throws DatabaseException
   */
  public void closeConnection(boolean commit) throws DatabaseException {
    try {
      if (commit) {
        connection.commit();
      }
      else {
        connection.rollback();
      }

      connection.close();
      connection = null;
    }
    catch (SQLException e) {
      throw new DatabaseException("closeConnection failed");
    }
  }

  /**
   * creates a table for recipes in the database if it has not already been created
   *
   * @throws SQLException
   */
  public void createRecipeTable() throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "create table if not exists recipes (" +
              "id integer not null primary key autoincrement," +
              "recipeBookId integer not null," +
              "name varchar(255) not null" +
              ")";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates a table for recipe books in the database if it has not already been created
   *
   * @throws SQLException
   */
  public void createRecipeBookTable() throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "create table if not exists recipeBooks (" +
              "id integer not null primary key autoincrement," +
              "name varchar(255) not null" +
              ")";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates a table for ingredients in the database if it has not already been created
   *
   * @throws SQLException
   */
  public void createIngredientTable() throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "create table if not exists ingredient (" +
              "id integer not null primary key autoincrement," +
              "name varchar(255) not null," +
              "averagePrice integer," +
              "salePrice integer," +
              "mostRecentPrice integer," +
              "amount integer," +
              "measurement varchar(32)" +
              ")";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates a table to show which ingredient and recipes go together in the database if it has not already been created
   *
   * @throws SQLException
   */
  public void createRecipeToIngredientsTable() throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "create table if not exists recipeToIngredients (" +
              "recipeId integer not null primary key," +
              "ingredientId integer not null" +
              ")";
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates all tables needed in the database for this program if they are not already made
   *
   * @throws SQLException
   */
  public void createTables() throws SQLException {
    createRecipeTable();
    createRecipeBookTable();
    createIngredientTable();
    createRecipeToIngredientsTable();
  }

  /**
   * adds a recipe to the recipes table in the database
   *
   * @param recipe
   * @return whether the recipe successfully uploaded to the database
   * @throws SQLException
   */
  public boolean insertRecipe(Recipe recipe) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipes (name) values (?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
        Statement keyStmt = connection.createStatement();
        ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
        keyRS.next();
        int id = keyRS.getInt(1);   // ID of the new book

        recipe.setId(id);

        return true;
      }
      else {
        return false;
      }
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * accesses the recipes out of the database
   *
   * @return names of recipes
   * @throws DatabaseException
   */
  public Set<String> loadRecipes() throws DatabaseException {
    try {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      try {
        String sql = "select name from recipes";
        stmt = connection.prepareStatement(sql);

        Set<String> names = new HashSet<>();
        rs = stmt.executeQuery();
        while (rs.next()) {
          String name = rs.getString(1);
          names.add(name);
        }

        return names;
      }
      finally {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      }
    }
    catch (SQLException e) {
      throw new DatabaseException("loadRecipes failed");
    }
  }
}
