package Recipe;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Recipe {

  private int id;

  private String name;

  private String mainIngredient;

  private String rating;

  public Recipe(String name, String mainIngredient, String rating) {
    this.name = name;
    this.mainIngredient = mainIngredient;
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMainIngredient() {
    return mainIngredient;
  }

  public void setMainIngredient(String mainIngredient) {
    this.mainIngredient = mainIngredient;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public void loadDatabaseDriver() throws ClassNotFoundException {

    // Name of class that implements database driver
    final String driver = "org.sqlite.JDBC";

    // Dynamically load the driver class
    Class.forName(driver);
  }

  private Connection connection;

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
              "mainIngredient varchar(255) not null,"+
              "rating varchar(32) not null" +
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
      String sql = "insert into recipes (name, mainIngredient, rating) values (?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, recipe.getName());
      stmt.setString(2, recipe.getMainIngredient());
      stmt.setString(3, recipe.getRating());

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


  public static void main(String[] args) {
    try {
      Scanner input = new Scanner(System.in);

      String recipeName = new String();

      String recipeMainIngredient = new String();

      String recipeRating = new String();

      System.out.println("How many recipes would you like to add?");
      int numRecipesToAdd = input.nextInt();

      for (int i = 0; i < numRecipesToAdd; ++i) {
        System.out.println("Enter the name of a recipe you would like to add:");
        recipeName = input.next();

        System.out.println("Enter the main ingredient of that recipe:");
        recipeMainIngredient = input.next();

        System.out.println("Enter a word describing how much you like that recipe:");
        recipeRating = input.next();

        Recipe recipe = new Recipe(recipeName, recipeMainIngredient, recipeRating);

        recipe.loadDatabaseDriver();
        recipe.openConnection();
        recipe.createTables();
        recipe.insertRecipe(recipe);
        Set<String> words = recipe.loadRecipes();
        recipe.closeConnection(true);
      }

      System.out.println("OK");
    }
    catch (DatabaseException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}

