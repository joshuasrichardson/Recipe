package Database;

import Ingredient.*;
import Recipe.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
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
  public void openConnection(String connectionURL) throws DatabaseException {
    try {
      final String CONNECTION_URL = connectionURL;

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
   * @param commit tells whether or not to commit all the changes to the database
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
   * creates a generic table
   *
   * @param tableName name of the table
   * @param primaryKey any object to become a primary key
   * @param primaryKeyType the type of the primary key
   * @param columnsAndTypes variables and their types to be included in the table
   * @throws SQLException if the syntax is wrong
   */
  public void createTable(String tableName, String primaryKey, String primaryKeyType, String... columnsAndTypes) throws SQLException {
    PreparedStatement stmt = null;
    try {
      StringBuilder sql = new StringBuilder("create table if not exists " + tableName + " (" +
              primaryKey + " " + primaryKeyType + " not null primary key, ");
      for (String string : columnsAndTypes) {
        sql.append(string + ", ");
      }
      sql.deleteCharAt(sql.length() - 1);
      sql.deleteCharAt(sql.length() - 1);
      sql.append(')');
      stmt = connection.prepareStatement(sql.toString());
      stmt.execute();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates all tables needed in the database for this program if they are not already made.
   *
   * @throws SQLException if there is a problem in the SQL syntax or something similar to that.
   */
  public void createTables() throws SQLException {
    createTable("recipes", "name", "varchar(255)",
            "recipeBookAuthor varchar(255)");
    createTable("recipeBooks", "author", "varchar(255)");
    createTable("ingredient", "name", "varchar(255)",
            "storageContainer varchar(255)", "averagePrice integer","salePrice integer",
            "mostRecentPrice integer", "amount integer", "measurement varchar(32)", "expirationDate varchar(255)",
            "brand varchar(255)", "foodGroup varchar(255)", "city varchar(255)");
    createTable("recipeToIngredients", "id", "integer",
            "recipeName varchar(255)", "ingredientName varchar(255)", "amount double", "units varchar(255)");
  }

  /**
   * adds a column into an existing table.
   * @param input the input from the keyboard or file with information about which table and column to update
   */
  public void addColumnToTable(Scanner input) throws SQLException {
    input.nextLine();
    System.out.println("Enter the name of the table to add to:");
    String tableName = input.nextLine();
    System.out.println("Enter the name of the column to add:");
    String columnName = input.nextLine();
    System.out.println("Enter the type of the column:");
    String columnType = input.nextLine();
    PreparedStatement stmt = null;
    try {
      String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " +  columnType;
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * adds a recipe to the recipes table in the database
   *
   * @param recipe the recipe to be inserted to the database
   * @return whether the recipe successfully uploaded to the database
   * @throws SQLException
   */
  public boolean insertRecipe(Recipe recipe, RecipeBook recipeBook) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipes (name, recipeBookAuthor) values (?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, recipeBook.getAuthor());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
        Statement keyStmt = connection.createStatement();
        ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
        keyRS.next();
        int id = keyRS.getInt(1);   // ID of the new recipe

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

  public boolean insertIngredient(Ingredient ingredient, Storage storage) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into ingredient (name, storageContainer, mostRecentPrice, amount, measurement, expirationDate," +
              "brand, foodGroup, city) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, ingredient.getName());
      stmt.setString(2, storage.getName());
      stmt.setDouble(3, ingredient.getMostRecentPrice());
      stmt.setDouble(4, ingredient.getAmount());
      stmt.setString(5, ingredient.getMeasurement());
      stmt.setString(6, ingredient.getExpirationDate().toString());
      stmt.setString(7, ingredient.getBrand());
      stmt.setString(8, ingredient.getFoodGroup());
      stmt.setString(9, ingredient.getCity());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
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

  public boolean insertRecipeToIngredient(Recipe recipe, Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipeToIngredients (recipeName, ingredientName, amount, units) values (?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, ingredient.getName());
      stmt.setDouble(3, ingredient.getAmount());
      stmt.setString(4, ingredient.getMeasurement());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 1) {
        Statement keyStmt = connection.createStatement();
        ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
        keyRS.next();
        int id = keyRS.getInt(1);   // ID of the new recipe

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
