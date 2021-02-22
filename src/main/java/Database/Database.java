package Database;

import Ingredient.*;
import Recipe.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * deals with adding, selecting, and removing things from the database to store information.
 */
public class Database {

  private Connection connection;

  /**
   * creates a database without a connection to be connected later.
   */
  public Database() {

  }

  /**
   * creates a Database object that is connected to a database.
   * @param connection the connection to the dataabse.
   */
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
   * @param connectionURL a String with the url to connect to
   * @return the connection
   * @throws SQLException
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
      createTable("User", "userID", "varchar(255)",
              "username varchar(255)", "password varchar(255)", "email varchar(255)",
              "firstName varchar(255)", "lastName varchar(255)");
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
  public ResultSet selectFromTable(String table, String column, Object value) throws SQLException {
    String sql = "SELECT * FROM " + table + " WHERE " + column + " = \'" + value + "\';";
    Statement keyStmt = connection.createStatement();
    return keyStmt.executeQuery(sql);
  }

  /**
   * selects items from the tables (first part of the strings), where the columns (second part of the strings)
   * equal the corresponding values (third part of string). Separate each part with a comma
   * (part 1,part 2,part 3).
   * @param tablesColumnsValues the tables, columns, and values to use in the select function.
   * @return a result set with the matching items.
   * @throws SQLException
   */
  public ResultSet selectUsingMultipleColumns(String... tablesColumnsValues) throws SQLException {
    ArrayList<String> tables = new ArrayList<>();
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    for (String tableColumnValue : tablesColumnsValues) {
      tables.add(findTable(tableColumnValue));
      columns.add(findColumn(tableColumnValue));
      values.add(findValue(tableColumnValue));
    }
    StringBuilder sql = new StringBuilder("SELECT * FROM ");
    for (int i = 0; i < tables.size(); ++i) {
      boolean sameTable = false;
      for (int j = 0; j < i; ++j) {
        if (tables.get(i).equals(tables.get(j))) {
          sameTable = true;
        }
      }
      if (!sameTable) sql.append(tables.get(i) + ", ");
    }
    sql.deleteCharAt(sql.length() - 2);
    sql.append("\nWHERE ");
    for (int i = 0; i < columns.size(); ++i) {
      if (i > 0) sql.append(" AND ");
      sql.append(columns.get(i) + " = \'" + values.get(i) + "\'");
    }
    Statement keyStmt = connection.createStatement();
    return keyStmt.executeQuery(sql.toString());
  }

  private String findTable(String tableColumnValue) {
    StringBuilder table = new StringBuilder(tableColumnValue);
    table.delete(table.indexOf(","), table.length());
    return table.toString();
  }

  private String findColumn(String tableColumnValue) {
    StringBuilder column = new StringBuilder(tableColumnValue);
    column.delete(0, column.indexOf(",") + 1);
    column.delete(column.indexOf(","), column.length());
    return column.toString();
  }

  private String findValue(String tableColumnValue) {
    StringBuilder value = new StringBuilder(tableColumnValue);
    value.delete(0, value.indexOf(",") + 1);
    value.delete(0, value.indexOf(",") + 1);
    return value.toString();
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

  /**
   * clears out the values of a single table
   * @param tableName the name of the table to clear
   * @throws SQLException
   */
  public void clearTable(String tableName) throws SQLException {
    PreparedStatement stmt = null;
    String sql = "DELETE FROM " + tableName + ";";
    stmt = connection.prepareStatement(sql);
    stmt.execute();
  }

  /**
   * clears all tables in the database.
   * @return whether it was successful
   * @throws SQLException
   */
  public boolean clearAllTables() throws SQLException
  {
    clearTable("User");
    clearTable("AuthTokens");
    return true;
  }
}
