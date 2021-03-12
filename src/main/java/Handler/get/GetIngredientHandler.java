package Handler.get;

import Server.Server;
import Request.GetIngredientRequest;
import Result.GetIngredientResult;
import Service.GetIngredientService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;

public class GetIngredientHandler implements HttpHandler {
  /**
   * handles and returns a single ingredient object in json format.
   * @param ingredientName the name of the ingredient to get.
   * @param authToken the authtoken of the user.
   * @return the person object in json format.
   */
  public String handleIngredient(String ingredientName, String authToken) {
    GetIngredientRequest request = new GetIngredientRequest(ingredientName, authToken);
    GetIngredientService service = new GetIngredientService();
    GetIngredientResult result = service.getIngredient(request);

    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    return decoder.toJson(result);
  }

  /**
   * handles and returns all person objects belonging to a user in json format.
   * @param authToken the authtoken of the user.
   * @return all person objects in json format.
   */
  public String handleIngredients(String authToken) {
    GetIngredientService service = new GetIngredientService();
    GetIngredientResult result = service.getAllIngredients(authToken);

    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    return decoder.toJson(result);
  }

  /**
   * handles the requests related to persons.
   * @param exchange the http exchange.
   * @throws IOException
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting to get ingredients.");
    GetHandler handler = new GetHandler();
    handler.handle(exchange, this);
  }
}
