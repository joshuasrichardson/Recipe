package Service;

import Database.*;
import Model.Ingredient;
import Request.AddIngredientRequest;

import java.sql.SQLException;
import java.util.ArrayList;

import static Server.Server.CONNECTION_URL;

public class Calculator {

  private final Database db = new Database();
  private final String connection;

  public Calculator(String connection) {
    this.connection = connection;
  }

  public static Ingredient combineIngredients(ArrayList<Ingredient> ingredients) {
    Ingredient ingredient = ingredients.get(0);

    for (int i = 1; i < ingredients.size(); ++i) {
      if (!ingredient.getUnit().equals(ingredients.get(i).getUnit())) {
        //TODO: convert units
      }
      ingredient.setAmount(ingredient.getAmount() + ingredients.get(i).getAmount());
      ingredient.setNumber(ingredient.getNumber() + ingredients.get(i).getNumber());
    }

    return ingredient;
  }

  public Double calculateAveragePrice(AddIngredientRequest request) {
    double newAvgPrice = 0;
    try {
      IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
      Ingredient ingredient = ingredientDAO.accessIngredientInformation(request.getIngredientName());

      double prevTotalSpent = (ingredient.getAveragePricePerUnit() * ingredient.getTotalAmountBought());

      //TODO:Convert units if they are different.

      newAvgPrice = (prevTotalSpent + request.getMostRecentPrice()) /
              (ingredient.getTotalAmountBought() + (request.getNumber() * request.getAmount()));

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (DatabaseAccessException e) {
      if (e.getMessage().equals("Ingredient doesn't exist.")) {
        newAvgPrice = request.getMostRecentPrice();
      }
      else e.printStackTrace();
    }
    finally {
      try {
        db.closeConnection(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return newAvgPrice;
  }

  public Double calculateSalePricePerUnit(AddIngredientRequest request) {
    double salePrice = request.getMostRecentPrice();
    double salePricePerUnit = salePrice / (request.getNumber() * request.getAmount());

    try {
      IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
      Ingredient ingredient = ingredientDAO.accessIngredientInformation(request.getIngredientName());
      if (salePricePerUnit > ingredient.getSalePricePerUnit()) {
        salePricePerUnit = ingredient.getSalePricePerUnit();
      }
    }
    catch (DatabaseAccessException e) {
      salePricePerUnit = salePrice / (request.getNumber() * request.getAmount());
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      try {
        db.closeConnection(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return salePricePerUnit;
  }

  public Double calculateTotalAmount(AddIngredientRequest request) {

    double totalAmount = 0.0;

    try {
      IngredientDAO ingredientDAO = new IngredientDAO(db.getConnection(connection));
      Ingredient ingredient = ingredientDAO.accessIngredientInformation(request.getIngredientName());
      totalAmount = ingredient.getTotalAmountBought() + (request.getAmount() * request.getNumber());
    }
    catch (DatabaseAccessException e) {
      totalAmount = 0.0;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      try {
        db.closeConnection(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return totalAmount;
  }

  //FIXME: get the cheapest store and the sale price from the database and then update the cheapest store if the new
  //one is cheaper.
  public String calculateCheapestStore(String mostRecentStore, Double mostRecentPrice) {
    return "Not implemented yet.";
  }


}
