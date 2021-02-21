package Service;

import Database.Database;
import Database.IngredientDAO;
import Ingredient.*;
import Request.IngredientRequest;
import Result.IngredientResult;

import java.sql.SQLException;

public class IngredientService {

  boolean commit;

  public IngredientService() {
    this.commit = true;
  }

  public IngredientService(boolean commit) {
    this.commit = commit;
  }

  public IngredientResult addIngredient(IngredientRequest request) {
    if (request.hasAllNecessaryFields().equals("大丈夫です")) {

      Ingredient newIngredient = createNewIngredient(request);
      Storage storage = new Storage(getUsername(request.getAuthToken()));
      Database db = new Database();

      try {
        IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection("jdbc:sqlite:" +
                getUsername(request.getAuthToken()) + ".sqlite"));
        ingredientDAO.createIngredientTable();
        ingredientDAO.addIngredientToTable(newIngredient, storage);

        return new IngredientResult(true, newIngredient.getName() + " was successfully added to " +
                newIngredient.getContainer() + "'s storage.");
      }
      catch (SQLException e) {
        return new IngredientResult(false, "Error: (while adding " + request.getName() + ") " + e.getMessage());
      }
      finally {
        try {
          db.closeConnection(commit);
        }
        catch (SQLException e) {
          return new IngredientResult(false, "Error: " + e.getMessage());
        }
      }
    }
    else {
      return new IngredientResult(false, "Error: Something is wrong with the user's " +
              request.hasAllNecessaryFields() + ".");
    }
  }

  private Ingredient createNewIngredient(IngredientRequest request) {
    Ingredient newIngredient = new Ingredient();
    newIngredient.setName(request.getName());
    newIngredient.setMostRecentPrice(request.getMostRecentPrice());
    newIngredient.setAveragePrice(calculateAveragePrice(request.getMostRecentPrice()));
    newIngredient.setSalePrice(calculateSalePrice(request.getMostRecentPrice()));
    newIngredient.setAmount(request.getAmount());
    newIngredient.setUnit(request.getUnit());
    newIngredient.setNumber(request.getNumber());
    newIngredient.setContainer(request.getContainer());
    newIngredient.setMostRecentStore(request.getMostRecentStore());
    newIngredient.setCheapestStore(calculateCheapestStore(request.getMostRecentStore(), request.getMostRecentPrice()));
    newIngredient.setExpirationDate(request.getExpirationDate());
    newIngredient.setBrand(request.getBrand());
    newIngredient.setCity(request.getCity());
    newIngredient.setFoodGroup(request.getFoodGroup());
    newIngredient.setAllergens(request.getAllergens());

    return newIngredient;
  }

  //FIXME: use the authToken to get the username out of the database.
  private String getUsername(String authToken) {
    return "RecipeTest";
  }

  //FIXME: get the current average, get the total number bought, calculate the new average, and return the new average.
  private Double calculateAveragePrice(Double mostRecentPrice) {
    return Double.valueOf(1);
  }

  //FIXME: compare it to the cheapest price per unit in the Database and return the cheaper one.
  private Double calculateSalePrice(Double mostRecentPrice) {
    return Double.valueOf(1);
  }

  //FIXME: get the cheapest store and the sale price from the database and then update the cheapest store if the new
  //one is cheaper.
  private String calculateCheapestStore(String mostRecentStore, Double mostRecentPrice) {
    return "Not implemented yet.";
  }
}
