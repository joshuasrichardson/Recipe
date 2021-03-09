package Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * deals with adding, selecting, and removing things from the database to store information.
 */
public class Database {

  private Connection connection;

  /**
   * creates a database without a connection to be connected later.
   */
  public Database() { }

  /**
   * creates a Database object that is connected to a database.
   * @param connection the connection to the database.
   */
  public Database(Connection connection) {
    this.connection = connection;
  }

  /**
   * connects to the database
   * @param connectionURL a String with the url to connect to
   * @return the connection
   * @throws SQLException
   */
  public Connection openConnection(String connectionURL) throws SQLException {
    connection = DriverManager.getConnection(connectionURL);
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
    }
    else {
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
      createTable("ingredientInformation", "ingredientID", "INTEGER",
              "ingredientName VARCHAR(255)", "brand VARCHAR(255)",
              "totalAmountBought DOUBLE", "averagePricePerUnit DOUBLE", "salePricePerUnit DOUBLE",
              "mostRecentPricePerUnit DOUBLE", "amount DOUBLE", "unit VARCHAR(32)",
              "foodGroup VARCHAR(255)", "cheapestStore VARCHAR(255)", "city VARCHAR(255)");
      createTable("ingredientInventory", "ingredientID", "INTEGER",
              "ingredientName VARCHAR(255)", "brand VARCHAR(255)", "owner VARCHAR(255)",
              "storageContainer VARCHAR(255)", "mostRecentPrice DOUBLE", "number INTEGER", "container VARCHAR(255)",
              "amount DOUBLE", "unit VARCHAR(32)", "purchaseDate VARCHAR(255)", "expirationDate VARCHAR(255)");
      createTable("recipeToIngredients", "id", "integer",
              "recipeName varchar(255)", "ingredientName varchar(255)", "amount double", "units varchar(255)");
      createTable("AuthTokens", "authToken", "varchar(255)",
              "username varchar(255)");
      createTable("User", "username", "varchar(255)",
              "password varchar(255)", "email varchar(255)",
              "firstName varchar(255)", "lastName varchar(255)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * adds a column into an existing table. Only used in development.
   * @param tableName the table to add the column to.
   * @param columnName the column to add.
   * @param columnType the type of column to add.
   * @throws SQLException if the column already exists or the table doesn't exist.
   */
  public void addColumnToTable(String tableName, String columnName, String columnType) throws SQLException {
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
   * changes a value in a table in the database
   * @param table the name of the table to update.
   * @param column the name of the column to update.
   * @param newValue the value to replace what is currently in the table.
   */
  /*public void updateDoubleColumn(String table, String primaryKeyName, String name, String column, Double newValue) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "UPDATE " + table + "\n" +
              "SET " + column + " = \'" + newValue + "\'\n" +
              "WHERE " + primaryKeyName + " = \'" + name + "\';";
      stmt = connection.prepareStatement(sql);
      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }*/

  /**
   * gets an ingredient with all the information from the database.
   * @param
   * @return
   */
  public ResultSet accessFromTable(String table, String column, Object value) throws SQLException {
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
  public ResultSet accessUsingMultipleColumns(String... tablesColumnsValues) throws SQLException {
    ArrayList<String> tables = new ArrayList<>();
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();

    for (String tableColumnValue : tablesColumnsValues) {
      tables.add(findTable(tableColumnValue));
      columns.add(findColumn(tableColumnValue));
      values.add(findValue(tableColumnValue));
    }

    StringBuilder sql = appendTables(tables);
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

    assert tableColumnValue.length() > 5;
    assert tableColumnValue.contains(",");

    StringBuilder table = new StringBuilder(tableColumnValue);
    table.delete(table.indexOf(","), table.length());
    return table.toString();
  }

  private String findColumn(String tableColumnValue) {

    assert tableColumnValue.length() > 5;
    assert tableColumnValue.contains(",");

    StringBuilder column = new StringBuilder(tableColumnValue);
    column.delete(0, column.indexOf(",") + 1);

    assert tableColumnValue.contains(",");

    column.delete(column.indexOf(","), column.length());
    return column.toString();
  }

  private String findValue(String tableColumnValue) {

    assert tableColumnValue.length() > 5;
    assert tableColumnValue.contains(",");

    StringBuilder value = new StringBuilder(tableColumnValue);
    value.delete(0, value.indexOf(",") + 1);

    assert tableColumnValue.contains(",");

    value.delete(0, value.indexOf(",") + 1);
    return value.toString();
  }

  private StringBuilder appendTables(ArrayList<String> tables) {
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
    return sql;
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
    clearTable("AuthToken");
    clearTable("IngredientInformation");
    clearTable("IngredientInventory");
    clearTable("Taxes");
    return true;
  }
}
