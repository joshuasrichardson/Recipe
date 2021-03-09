package Database;

import Model.Ingredient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

import static Main.Server.logger;

public class IngredientDAO {

  Connection connection;
  Database db;

  /**
   * creates a new Ingredient data access object with a connection to a database.
   * @param connection the connection to the database.
   */
  public IngredientDAO(Connection connection) {
    this.connection = connection;
    this.db = new Database(connection);
  }

  /**
   * creates a table to store information about an ingredient.
   * @throws SQLException
   */
  public void createIngredientInformationTable() throws SQLException {
    db.createTable("ingredientInformation", "ingredientID", "INTEGER",
            "ingredientName VARCHAR(255)", "brand VARCHAR(255)",
            "totalAmountBought DOUBLE", "averagePricePerUnit DOUBLE", "salePricePerUnit DOUBLE",
            "mostRecentPricePerUnit DOUBLE", "unit VARCHAR(32)", "foodGroup VARCHAR(255)",
            "cheapestStore VARCHAR(255)", "city VARCHAR(255)");
  }

  /**
   * creates a table to store information about ingredients in the user's inventory.
   * @throws SQLException
   */
  public void createIngredientInventoryTable() throws SQLException {
    db.createTable("ingredientInventory", "ingredientID", "INTEGER",
            "ingredientName VARCHAR(255)", "brand VARCHAR(255)", "owner VARCHAR(255)",
            "storageContainer VARCHAR(255)", "mostRecentPrice DOUBLE", "number INTEGER", "container VARCHAR(255)",
            "amount DOUBLE", "unit VARCHAR(32)", "purchaseDate VARCHAR(255)", "expirationDate VARCHAR(255)");
  }

  /**
   * adds an ingredient to the ingredientInformation table.
   *
   * @param ingredient the ingredient to add.
   * @throws SQLException
   */
  public void addIngredientToInformationTable(Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    //FIXME: check to see if it's already in the table, and then if it is, just update it without adding a new one.
    try {
      String sql = "insert into ingredientInformation (ingredientName, brand, totalAmountBought, " +
              "averagePricePerUnit, salePricePerUnit, mostRecentPricePerUnit, unit, foodGroup, cheapestStore, city) " +
              "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setString(1, ingredient.getName());
      stmt.setString(2, ingredient.getBrand());
      stmt.setDouble(3, ingredient.getTotalAmountBought());
      stmt.setDouble(4, ingredient.getAveragePricePerUnit());
      stmt.setDouble(5, ingredient.getSalePricePerUnit());
      stmt.setDouble(6, ingredient.getMostRecentPricePerUnit());
      stmt.setString(7, ingredient.getUnit());
      stmt.setString(8, ingredient.getFoodGroup());
      stmt.setString(9, ingredient.getCheapestStore());
      stmt.setString(10, ingredient.getCity());

      stmt.executeUpdate();

    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public void addIngredientToInventoryTable(Ingredient ingredient) throws SQLException {
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

      stmt.executeUpdate();

    } finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  public void removeFromInformationTable(Ingredient ingredient) throws SQLException {
    PreparedStatement stmt = null;
    logger.log(Level.INFO, "Removing " + ingredient.getName());
    try {
      String sql = "DELETE FROM ingredientInformation " +
              "WHERE ingredientName = '" + ingredient.getName() + "' AND " +
              "brand = '" + ingredient.getBrand() + "';";

      stmt = connection.prepareStatement(sql);
      stmt.executeUpdate();
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * finds and returns all the information about an ingredient from the database tables.
   * @param ingredientName the ingredient to get.
   * @return an array with all that type of ingredient.
   */
  public ArrayList<Ingredient> accessIngredientFromTables(String ingredientName, String username) {
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    try {
      ResultSet resultSet = db.accessFromTable("ingredientInformation", "ingredientName", ingredientName);
      Ingredient ingredient = setIngredientInformation(resultSet);
      resultSet = db.accessUsingMultipleColumns("ingredientInventory,ingredientName," + ingredientName,
              "ingredientInventory,owner," + username);
      ingredients = setIngredientInventory(ingredient, resultSet);
    }
    catch (SQLException e) {
      System.out.println("Ingredient doesn't exist.");
    }
    return ingredients;
  }

  /**
   * finds and returns all the information about an ingredient from the database tables.
   * @param ingredientName
   * @return
   */
  public Ingredient accessIngredientInformation(String ingredientName) throws DatabaseAccessException {
    Ingredient ingredient = new Ingredient();

    try {
      ResultSet resultSet = db.accessFromTable("ingredientInformation", "ingredientName", ingredientName);
      ingredient = setIngredientInformation(resultSet);
    }
    catch (SQLException e) {
      throw new DatabaseAccessException("Ingredient doesn't exist.");
    }
    return ingredient;
  }

  private Ingredient setIngredientInformation(ResultSet keyRS) {
    Ingredient ingredient = new Ingredient();
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
   * @param generalIngredient the ingredient that holds the information
   * @param keyRS the result with all the ingredients in the inventory
   * @return a list of ingredients in the inventory
   */
  private ArrayList<Ingredient> setIngredientInventory(Ingredient generalIngredient, ResultSet keyRS) {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    boolean atLeastOneInInventory = false;
    try {
      while (keyRS.next()) {
        Ingredient ingredient = new Ingredient(generalIngredient);
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
    if (!atLeastOneInInventory) ingredients.add(generalIngredient);

    return ingredients;
  }

  public Ingredient accessUsingNameAndBrand(String ingredientName, String ingredientBrand) throws SQLException {
    ResultSet resultSet = db.accessUsingMultipleColumns(
            "ingredientInformation,ingredientName," + ingredientName,
            "ingredientInformation,brand," + ingredientBrand);
    return setIngredientInformation(resultSet);
  }

}
