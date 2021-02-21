package Database;

import Ingredient.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IngredientDAO {

  Connection connection;

  public IngredientDAO(Connection connection) {
    this.connection = connection;
  }

  public void createIngredientTable() throws SQLException {
    Database db = new Database(this.connection);

    db.createTable("ingredient", "ingredientID", "integer",
            "name varchar(255)", "storageContainer varchar(255)", "averagePrice integer",
            "salePrice integer", "mostRecentPrice integer", "amount integer", "measurement varchar(32)",
            "expirationDate varchar(255)", "brand varchar(255)", "foodGroup varchar(255)", "city varchar(255)");
  }

  /**
   * adds an ingredient to the ingredient table.
   *
   * @param ingredient the ingredient to add.
   * @param storage    the container that holds the ingredient (name of person or type of container like fridge).
   * @return whether it was able to enter.
   * @throws SQLException if it already exists.
   */
  public boolean addIngredientToTable(Ingredient ingredient, Storage storage) throws SQLException {
    PreparedStatement stmt = null;
    try {
      String sql = "insert into ingredient (ingredientID, name, storageContainer, mostRecentPrice, amount, measurement, expirationDate," +
              "brand, foodGroup, city) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      stmt = connection.prepareStatement(sql);
      stmt.setInt(1, ingredient.getIngredientID());
      stmt.setString(2, ingredient.getName());
      stmt.setString(3, storage.getName());
      stmt.setDouble(4, ingredient.getMostRecentPrice());
      stmt.setDouble(5, ingredient.getAmount());
      stmt.setString(6, ingredient.getUnit());
      stmt.setString(7, ingredient.getExpirationDate().toString());
      stmt.setString(8, ingredient.getBrand());
      stmt.setString(9, ingredient.getFoodGroup());
      stmt.setString(10, ingredient.getCity());

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

}
