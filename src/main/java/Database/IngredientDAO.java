package Database;

import Ingredient.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class IngredientDAO {

  Connection connection;

  /**
   * creates a new Ingredient data access object with no connection to be connected later.
   */
  public IngredientDAO() {

  }

  /**
   * creates a new Ingredient data access object with a connection to a database.
   * @param connection the connection to the database.
   */
  public IngredientDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * creates a table to store information about an ingredient.
   * @throws SQLException
   */
  public void createIngredientInformationTable() throws SQLException {
    Database db = new Database(this.connection);

    db.createTable("ingredientInformation", "ingredientID", "INTEGER",
            "ingredientName VARCHAR(255)", "brand VARCHAR(255)",
            "totalAmountBought DOUBLE", "averagePricePerUnit DOUBLE", "salePricePerUnit DOUBLE",
            "mostRecentPricePerUnit DOUBLE", "amount DOUBLE", "unit VARCHAR(32)",
            "foodGroup VARCHAR(255)", "cheapestStore VARCHAR(255)", "city VARCHAR(255)");
  }

  /**
   * creates a table to store information about ingredients in the user's inventory.
   * @throws SQLException
   */
  public void createIngredientInventoryTable() throws SQLException {
    Database db = new Database(this.connection);

    db.createTable("ingredientInventory", "ingredientID", "INTEGER",
            "ingredientName VARCHAR(255)", "brand VARCHAR(255)", "owner VARCHAR(255)",
            "storageContainer VARCHAR(255)", "mostRecentPrice DOUBLE", "number INTEGER", "container VARCHAR(255)",
            "amount DOUBLE", "unit VARCHAR(32)", "purchaseDate VARCHAR(255)", "expirationDate VARCHAR(255)");
  }

  /**
   * adds an ingredient to the ingredientInformation table.
   *
   * @param ingredient the ingredient to add.
   * @return whether it was able to enter.
   * @throws SQLException
   */
  public boolean addIngredientToInformationTable(Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    //FIXME: check to see if it's already in the table, and then if it is, just update it without adding a new one.
    try {
      String sql = "insert into ingredientInformation (ingredientName, brand, totalAmountBought, " +
              "averagePricePerUnit, salePricePerUnit, mostRecentPricePerUnit, amount, unit, foodGroup, cheapestStore, city) " +
              "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, ingredient.getName());
      stmt.setString(2, ingredient.getBrand());
      stmt.setDouble(3, ingredient.getTotalAmountBought());
      stmt.setDouble(4, ingredient.getAveragePricePerUnit());
      stmt.setDouble(5, ingredient.getSalePricePerUnit());
      stmt.setDouble(6, ingredient.getMostRecentPricePerUnit());
      stmt.setDouble(7, ingredient.getAmount());
      stmt.setString(8, ingredient.getUnit());
      stmt.setString(9, ingredient.getFoodGroup());
      stmt.setString(10, ingredient.getCheapestStore());
      stmt.setString(11, ingredient.getCity());

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

  public boolean addIngredientToInventoryTable(Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into ingredientInventory (ingredientName, brand, owner, storageContainer, mostRecentPrice, " +
              "number, container, amount, unit, purchaseDate, expirationDate) " +
              "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, ingredient.getName());
      stmt.setString(2, ingredient.getBrand());
      stmt.setString(3, ingredient.getOwner());
      stmt.setString(4, ingredient.getStorageContainer());
      stmt.setDouble(5, ingredient.getMostRecentPrice());
      stmt.setInt(6, ingredient.getNumber());
      stmt.setString(7, ingredient.getContainer());
      stmt.setDouble(8, ingredient.getAmount());
      stmt.setString(9, ingredient.getUnit());
      stmt.setString(10, ingredient.getPurchaseDate().toString());
      stmt.setString(11, ingredient.getExpirationDate().toString());

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
   * finds and returns all the information about an ingredient from the database tables.
   * @param ingredientName
   * @return
   */
  public ArrayList<Ingredient> getIngredientFromTables(String ingredientName) {
    Database db = new Database(this.connection);
    Ingredient ingredient = new Ingredient();
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    try {
      ResultSet resultSet = db.selectFromTable("ingredientInformation", "ingredientName", ingredientName);
      ingredient = setIngredientInformation(ingredient, resultSet);
      resultSet = db.selectFromTable("ingredientInventory", "ingredientName", ingredientName);
      ingredients = setIngredientInventory(ingredient, resultSet);
    }
    catch (SQLException e) {
      System.out.println("Ingredient doesn't exist. Creating new ingredient.");
    }
    return ingredients;
  }

  private Ingredient setIngredientInformation(Ingredient ingredient, ResultSet keyRS) {
    try {
      while(keyRS.next()) {
        int ingredientID = keyRS.getInt(1);
        ingredient.setIngredientID(ingredientID);
        String ingredientName = keyRS.getString(2);
        ingredient.setName(ingredientName);
        String brand = keyRS.getString(3);
        ingredient.setBrand(brand);
        double totalAmountBought = keyRS.getDouble(4);
        ingredient.setTotalAmountBought(totalAmountBought);
        double averagePricePerUnit = keyRS.getDouble(5);
        ingredient.setAveragePricePerUnit(averagePricePerUnit);
        double salePricePerUnit = keyRS.getDouble(6);
        ingredient.setSalePricePerUnit(salePricePerUnit);
        double mostRecentPricePerUnit = keyRS.getDouble(7);
        ingredient.setMostRecentPricePerUnit(mostRecentPricePerUnit);
        double amount = keyRS.getDouble(8);
        ingredient.setAmount(amount);
        String unit = keyRS.getString(9);
        ingredient.setUnit(unit);
        String foodGroup = keyRS.getString(10);
        ingredient.setFoodGroup(foodGroup);
        String cheapestStore = keyRS.getString(11);
        ingredient.setCheapestStore(cheapestStore);
        String city = keyRS.getString(12);
        ingredient.setCity(city);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return ingredient;
  }

  /**
   *
   * @param ingredient the ingredient that holds the information
   * @param keyRS the result with all the ingredients in the inventory
   * @return a list of ingredients in the inventory
   */
  private ArrayList<Ingredient> setIngredientInventory(Ingredient ingredient, ResultSet keyRS) {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    boolean atLeastOneInInventory = false;
    try {
      while (keyRS.next()) {
        int ingredientID = keyRS.getInt(1);
        ingredient.setIngredientID(ingredientID);
        String ingredientName = keyRS.getString(2);
        ingredient.setName(ingredientName);
        String brand = keyRS.getString(3);
        ingredient.setBrand(brand);
        String owner = keyRS.getString(4);
        ingredient.setOwner(owner);
        String storageContainer = keyRS.getString(5);
        ingredient.setStorageContainer(storageContainer);
        double mostRecentPrice = keyRS.getDouble(6);
        ingredient.setMostRecentPrice(mostRecentPrice);
        int number = keyRS.getInt(7);
        ingredient.setNumber(number);
        String container = keyRS.getString(8);
        ingredient.setContainer(container);
        double amount = keyRS.getDouble(9);
        ingredient.setAmount(amount);
        String unit = keyRS.getString(10);
        ingredient.setUnit(unit);
        String purchaseDate = keyRS.getString(11);
        ingredient.setPurchaseDate(LocalDate.parse(purchaseDate));
        String expirationDate = keyRS.getString(12);
        ingredient.setExpirationDate(LocalDate.parse(expirationDate));

        ingredients.add(ingredient);

        atLeastOneInInventory = true;
      }
    }
    catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    if (!atLeastOneInInventory) ingredients.add(ingredient);

    return ingredients;
  }

  public Ingredient selectUsingNameAndBrand(String ingredientName, String ingredientBrand) throws SQLException {
    Database db = new Database(this.connection);
    Ingredient ingredient = new Ingredient();
    ResultSet resultSet = db.selectUsingMultipleColumns(
            "ingredientInformation,ingredientName," + ingredientName,
            "ingredientInformation,brand," + ingredientBrand);
    return setIngredientInformation(ingredient, resultSet);
  }

}
