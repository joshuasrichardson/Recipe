package Service;

import Database.Database;
import Database.IngredientDAO;
import Ingredient.*;
import Request.IngredientRequest;
import Result.IngredientResult;

import java.sql.SQLException;
import java.time.LocalDate;

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
      //Storage storage = new Storage(getUsername(request.getAuthToken()));
      Database db = new Database();

      try {
        IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection("jdbc:sqlite:" +
                getUsername(/*request.getAuthToken())*/"s") + ".sqlite"));
        ingredientDAO.createIngredientInformationTable();
        ingredientDAO.createIngredientInventoryTable();
        db.closeConnection(commit);

        ingredientDAO = new IngredientDAO(db.getConnection("jdbc:sqlite:" +
                getUsername(/*request.getAuthToken())*/"s") + ".sqlite"));
        ingredientDAO.addIngredientToInformationTable(newIngredient);
        ingredientDAO.addIngredientToInventoryTable(newIngredient);

        return new IngredientResult(true, newIngredient.getName() + " was successfully added to " +
                newIngredient.getOwner() + "'s storage.");
      }
      catch (SQLException e) {
        return new IngredientResult(false, "Error: (while adding " + request.getIngredientName() + ") " + e.getMessage());
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
    newIngredient.setName(request.getIngredientName());
    newIngredient.setOwner(findOwner("authToken"));//FIXME: replace authToken
    newIngredient.setMostRecentPrice(request.getMostRecentPrice());
    newIngredient.setAveragePricePerUnit(calculateAveragePrice(request.getMostRecentPrice()));
    newIngredient.setSalePricePerUnit(calculateSalePrice(request.getMostRecentPrice()));
    newIngredient.setAmount(request.getAmount());
    newIngredient.setUnit(request.getUnit());
    newIngredient.setNumber(request.getNumber());
    newIngredient.setContainer(request.getContainer());
    newIngredient.setMostRecentStore(request.getMostRecentStore());
    newIngredient.setCheapestStore(calculateCheapestStore(request.getMostRecentStore(), request.getMostRecentPrice()));
    newIngredient.setPurchaseDate(LocalDate.now());
    newIngredient.setExpirationDate(LocalDate.parse(request.getExpirationDate()));
    newIngredient.setBrand(request.getBrand());
    newIngredient.setCity(request.getCity());
    newIngredient.setFoodGroup(request.getFoodGroup());
    newIngredient.setStorageContainer(request.getStorageContainer());
    newIngredient.setAllergens(request.getAllergens());

    return newIngredient;
  }

  //FIXME: go find the user who is connected to the token, and return their first and last name.
  private String findOwner(String authToken) {
    return "Megumi Sakae";
  }

  //FIXME: use the authToken to get the username out of the database.
  private String getUsername(String authToken) {
    return "recipeTest";
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
