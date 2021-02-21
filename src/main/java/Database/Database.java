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

  public Database() {

  }

  public Database(Connection connection) {
    this.connection = connection;
  }

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
   */
  public Connection openConnection(String connectionURL) throws SQLException {
    final String CONNECTION_URL = connectionURL;
    connection = DriverManager.getConnection(CONNECTION_URL);
    connection.setAutoCommit(false);
    return connection;
  }

  /**
   * gets a connection to the database if open, and if it's not open, it opens one.
   * @return the connection to the database
   */
  public Connection getConnection(String connectionURL) throws SQLException {
    if(connection == null) {
      return openConnection(connectionURL);
    }
    else {
      return connection;
    }
  }

  /**
   * cuts off the connection to the database
   *
   * @param commit tells whether or not to commit all the changes to the database
   */
  public void closeConnection(boolean commit) throws SQLException {
    if (commit) {
      connection.commit();
    } else {
      connection.rollback();
    }

    connection.close();
    connection = null;
  }

  /**
   * creates a generic table
   *
   * @param tableName       name of the table
   * @param primaryKey      any object to become a primary key
   * @param primaryKeyType  the type of the primary key
   * @param columnsAndTypes variables and their types to be included in the table
   * @throws SQLException if the syntax is wrong
   */
  public void createTable(String tableName, String primaryKey, String primaryKeyType, String... columnsAndTypes) throws SQLException {
    PreparedStatement stmt = null;
    try {
      StringBuilder sql = new StringBuilder("create table if not exists " + tableName + " (" +
              primaryKey + " " + primaryKeyType + " not null primary key");
      if (primaryKeyType.equals("integer")) sql.append(" autoincrement");
      sql.append(", ");
      for (String string : columnsAndTypes) {
        sql.append(string + ", ");
      }
      sql.deleteCharAt(sql.length() - 1);
      sql.deleteCharAt(sql.length() - 1);
      sql.append(')');
      stmt = connection.prepareStatement(sql.toString());
      stmt.execute();
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * creates all tables needed in the database for this program if they are not already made.
   */
  public void createTables() {
    try {
      createTable("recipes", "name", "varchar(255)",
              "recipeBookAuthor varchar(255)");
      createTable("recipeBooks", "author", "varchar(255)");
      createTable("ingredient", "ingredientID", "integer",
              "name varchar(255)", "storageContainer varchar(255)", "averagePrice integer",
              "salePrice integer", "mostRecentPrice integer", "amount integer", "measurement varchar(32)",
              "expirationDate varchar(255)", "brand varchar(255)", "foodGroup varchar(255)", "city varchar(255)");
      createTable("recipeToIngredients", "id", "integer",
              "recipeName varchar(255)", "ingredientName varchar(255)", "amount double", "units varchar(255)");
      createTable("AuthTokens", "authToken", "varchar(255)",
              "userID varchar(255)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * adds a column into an existing table.
   *
   * @param input the input from the keyboard or file with information about which table and column to update.
   * @throws SQLException if the column already exists or the table doesn't exist.
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
      String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnType;
      stmt = connection.prepareStatement(sql);
      stmt.execute();
    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * adds a recipe to the recipes table in the database.
   *
   * @param recipe the recipe to be inserted to the database.
   * @return whether the recipe successfully uploaded to the database.
   * @throws SQLException if the recipe already exists.
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
  public boolean insertRecipeToIngredient(Recipe recipe, Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into recipeToIngredients (recipeName, ingredientName, amount, units) values (?, ?, ?, ?)";

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

  /**
   * changes a value in a table in the database
   * @param table the name of the table to update.
   * @param column the name of the column to update.
   * @param newValue the value to replace what is currently in the table.
   */
  public void updateDoubleColumn(String table, String primaryKeyName, String name, String column, Double newValue) throws SQLException {
    PreparedStatement stmt = null;
    try {
      //String sql = "update (?) set (?) = (?) where name = (?)";
      String sql = "UPDATE " + table + "\n" +
              "SET " + column + " = \'" + newValue + "\'\n" +
              "WHERE " + primaryKeyName + " = \'" + name + "\';";
      stmt = connection.prepareStatement(sql);
      //stmt.setString(1, table);
      //stmt.setString(2, column);
      //stmt.setDouble(3, newValue);
      //stmt.setString(4, name);
      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * gets an ingredient with all the information from the database.
   * @param
   * @return
   */
  public Object selectFromTable(String table, String primaryKey, Object primaryKeyValue) {
    String sql = "select * from " + table + " where " + primaryKey + " = \'" + primaryKeyValue + "\';";
    Object obj = null;
    try {
      Statement keyStmt = connection.createStatement();
      ResultSet keyRS = keyStmt.executeQuery(sql);

      if (table.equals("ingredient")) {
        obj = setIngredient(keyRS);
      }
      else if (table.equals("recipes")) {

      }
      else if (table.equals("recipeBook")) {

      }
      else if (table.equals("recipeToIngredients")) {

      }
      else {
        throw new Exception("Not a valid table name.");
      }
    }
    catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return obj;
  }

  //FIXME: change the order of the numbers to make sense
  public Ingredient setIngredient(ResultSet keyRS) {
    Ingredient ingredient = new Ingredient();
    try {
      while(keyRS.next()) {
        String name = keyRS.getString(1);
        ingredient.setName(name);
        double averagePrice = keyRS.getDouble(3);
        ingredient.setAveragePrice(averagePrice);
        double salePrice = keyRS.getDouble(4);
        ingredient.setSalePrice(salePrice);
        double mostRecentPrice = keyRS.getDouble(5);
        ingredient.setMostRecentPrice(mostRecentPrice);
        double amount = keyRS.getDouble(6);
        ingredient.setAmount(amount);
        String measurement = keyRS.getString(7);
        ingredient.setUnit(measurement);
        int number = keyRS.getInt(12);
        ingredient.setNumber(number);
        String container = keyRS.getString(13);
        ingredient.setContainer(container);
        String cheapestStore = keyRS.getString(15);
        ingredient.setCheapestStore(cheapestStore);
        String mostRecentStore = keyRS.getString(14);
        ingredient.setMostRecentStore(mostRecentStore);
        String expirationDateString = keyRS.getString(8);
        Date expirationDate = parseDate(expirationDateString);
        ingredient.setExpirationDate(expirationDate);
        String brand = keyRS.getString(9);
        ingredient.setBrand(brand);
        String foodGroup = keyRS.getString(10);
        ingredient.setFoodGroup(foodGroup);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return ingredient;
  }

  //FIXME:: Find the date in the string and make it into a date type
  private Date parseDate(String dateString) {
    System.out.println(dateString);
    return new Date(2021, 02, 21);
  }

  /**
   * accesses the recipes out of the database
   *
   * @return names of recipes
   */
  public Set<String> loadRecipes() {
    Set<String> names = null;
    try {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      try {
        String sql = "select name from recipes";
        stmt = connection.prepareStatement(sql);

        names = new HashSet<>();
        rs = stmt.executeQuery();
        while (rs.next()) {
          String name = rs.getString(1);
          names.add(name);
        }

        return names;
      } finally {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return names;
  }


}
