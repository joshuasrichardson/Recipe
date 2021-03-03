package Service;

import Database.*;
import Ingredient.*;
import Model.AuthToken;
import Model.User;
import Request.AddIngredientRequest;
import Result.AddIngredientResult;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

import static Main.Server.CONNECTION_URL;
import static Main.Server.logger;

public class AddIngredientService {

  private String connection;

  Database db = new Database();

  public AddIngredientService() {
    this.connection = CONNECTION_URL;
  }

  public AddIngredientService(String connection) {
    this.connection = connection;
  }

  public AddIngredientResult addIngredient(String authToken, AddIngredientRequest request) {
    logger.log(Level.INFO, "Adding an ingredient.");

    if (request.hasAllNecessaryFields().equals("大丈夫です")) {
      try {
        Ingredient newIngredient = createNewIngredient(authToken, request);

        IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection("jdbc:sqlite:storage.sqlite"));
        ingredientDAO.createIngredientInformationTable();
        ingredientDAO.createIngredientInventoryTable();
        db.closeConnection(true);

        ingredientDAO = new IngredientDAO(db.getConnection("jdbc:sqlite:storage.sqlite"));
        ingredientDAO.addIngredientToInformationTable(newIngredient);
        ingredientDAO.addIngredientToInventoryTable(newIngredient);

        return new AddIngredientResult(true, newIngredient.getName() + " was successfully added to " +
                newIngredient.getOwner() + "'s storage.");
      }
      catch (SQLException | DatabaseAccessException e) {
        return new AddIngredientResult(false, "Error: (while adding " + request.getIngredientName() + ") " + e.getMessage());
      }
      finally {
        try {
          db.closeConnection(true);
        }
        catch (SQLException e) {
          logger.log(Level.WARNING, "Couldn't close the database connection.");
          e.printStackTrace();
        }
      }
    }
    else {
      return new AddIngredientResult(false, "Error: Something is wrong with the user's " +
              request.hasAllNecessaryFields() + ".");
    }
  }

  private Ingredient createNewIngredient(String authToken, AddIngredientRequest request) throws SQLException, DatabaseAccessException {
    Ingredient newIngredient = new Ingredient();
    newIngredient.setName(request.getIngredientName());
    newIngredient.setOwner(findOwner(authToken).getFirstName());
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

  private User findOwner(String authToken) throws SQLException, DatabaseAccessException {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection(connection));
    AuthToken authTokenObject = authTokenDAO.accessAuthTokenFromTable(authToken);
    UserDAO userDAO = new UserDAO(db.getConnection(connection));
    User user = userDAO.accessUserFromTable(authTokenObject.getUsername());
    return user;
  }

  //FIXME: get the current average, get the total number bought, calculate the new average, and return the new average.
  private Double calculateAveragePrice(Double mostRecentPrice) throws SQLException {
    IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
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
