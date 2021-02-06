package Recipe;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
  private Connection connection;

  public void loadDatabaseDriver() throws ClassNotFoundException {

    // Name of class that implements database driver
    final String driver = "org.sqlite.JDBC";

    // Dynamically load the driver class
    Class.forName(driver);
  }

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

  public void createTables() throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "create table if not exists recipes (" +
              "id integer not null primary key autoincrement," +
              "name varchar(255) not null," +
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
