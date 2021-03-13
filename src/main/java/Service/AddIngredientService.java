package Service;

import Database.*;
import Model.Ingredient;
import Request.AddIngredientRequest;
import Result.AddIngredientResult;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

import static Server.Server.CONNECTION_URL;
import static Server.Server.logger;

public class AddIngredientService {

  private final String connection;

  private final Database db = new Database();

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

        IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
        ingredientDAO.createIngredientInformationTable();
        ingredientDAO.createIngredientInventoryTable();
        //db.closeConnection(true);

        //ingredientDAO = new IngredientDAO(db.getConnection(connection));
        ingredientDAO.removeFromInformationTable(newIngredient);
        //db.closeConnection(true);

        //ingredientDAO = new IngredientDAO(db.getConnection(connection));
        ingredientDAO.addIngredientToInformationTable(newIngredient);
        ingredientDAO.addIngredientToInventoryTable(newIngredient);

        return new AddIngredientResult(true, newIngredient.getName() + " ($" + newIngredient.getMostRecentPrice() +
                " for "  + newIngredient.getNumber() + " " + newIngredient.getContainer() + " with " +
                newIngredient.getAmount() + " " + newIngredient.getUnit() + " in each) was successfully added to " +
                newIngredient.getOwner() + " storage.");
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
    Calculator calc = new Calculator(connection);

    //TODO: Match the units of the request and the corresponding item in the database.

    Ingredient newIngredient = new Ingredient();
    newIngredient.setName(request.getIngredientName());
    newIngredient.setOwner(new Service(connection).findOwner(authToken).getUsername());
    newIngredient.setMostRecentPrice(request.getMostRecentPrice());
    newIngredient.setMostRecentPricePerUnit(request.getMostRecentPrice() / (request.getNumber() * request.getAmount()));
    newIngredient.setAveragePricePerUnit(calc.calculateAveragePrice(request));
    newIngredient.setSalePricePerUnit(calc.calculateSalePricePerUnit(request));
    newIngredient.setAmount(request.getAmount());
    newIngredient.setTotalAmountBought(calc.calculateTotalAmount(request));
    newIngredient.setUnit(request.getUnit());
    newIngredient.setNumber(request.getNumber());
    newIngredient.setContainer(request.getContainer());
    newIngredient.setMostRecentStore(request.getMostRecentStore());
    newIngredient.setCheapestStore(calc.calculateCheapestStore(request.getMostRecentStore(), request.getMostRecentPrice()));
    newIngredient.setPurchaseDate(LocalDate.now());
    newIngredient.setExpirationDate(LocalDate.parse(request.getExpirationDate()));
    newIngredient.setBrand(request.getBrand());
    newIngredient.setCity(request.getCity());
    newIngredient.setFoodGroup(request.getFoodGroup());
    newIngredient.setStorageContainer(request.getStorageContainer());
    newIngredient.setAllergens(request.getAllergens());

    return newIngredient;
  }

}
