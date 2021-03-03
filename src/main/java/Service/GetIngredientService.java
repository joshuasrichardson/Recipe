package Service;

import Database.*;
import Ingredient.Ingredient;
import Model.AuthToken;
import Model.User;
import Request.GetIngredientRequest;
import Result.GetIngredientResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import static Main.Server.CONNECTION_URL;
import static Main.Server.logger;

public class GetIngredientService {

  private String connection;

  Database db = new Database();

  public GetIngredientService() {
    this.connection = CONNECTION_URL;
  }

  public GetIngredientService(String connection) {
    this.connection = connection;
  }

  /**
   * gets a person from the database with the specified personID.
   * @param request the request for the person.
   * @return the result of whether the request succeeded or not. If it succeeded, a person's information is sent.
   */
  public GetIngredientResult getIngredient(GetIngredientRequest request) {
    logger.log(Level.INFO, "Getting an ingredient.");

    if (request.getName() == null) return new GetIngredientResult(false, "Error: No ingredient name provided.");
    if (request.getAuthtoken() == null) return new GetIngredientResult(false, "Error: No authToken provided.");
    try {
      String username = retrieveAuthTokenUserUsername(request);

      IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
      ArrayList<Ingredient> ingredients = ingredientDAO.getIngredientFromTables(request.getName(), username);

      Ingredient ingredient = ingredients.get(0);//FIXME::

      return new GetIngredientResult(true, ingredient);
    }
    catch (DatabaseAccessException e) {
      return new GetIngredientResult(false, "Error: " + e.getMessage());
    }
    catch (SQLException throwables) {
      return new GetIngredientResult(false, "Error: " + throwables.getMessage());
    }
    finally {
      try {
        db.closeConnection(true);
      }
      catch (SQLException throwables) {
        logger.log(Level.WARNING, "Couldn't close connection.");
        throwables.printStackTrace();
      }
    }
  }

  private String retrieveAuthTokenUserUsername(GetIngredientRequest request) throws DatabaseAccessException, SQLException {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection(connection));
    AuthToken authToken = authTokenDAO.accessAuthTokenFromTable(request.getAuthtoken());
    UserDAO userDAO = new UserDAO(db.getConnection(connection));
    User user = userDAO.accessUserFromTable("username", authToken.getUsername());
    return user.getUsername();
  }

  public GetIngredientResult getAllIngredients(String authtoken) {
    return null;//TODO
  }
}
