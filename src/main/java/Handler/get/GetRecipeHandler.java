package Handler.get;

import Result.GetRecipeResult;
import Server.Server;
import Service.GetRecipeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;

import static Handler.get.GetHandler.fixInput;

public class GetRecipeHandler implements HttpHandler {

  public String handleRecipe(String recipeName) {
    recipeName = fixInput(recipeName);
    System.out.println(recipeName);

    GetRecipeService service = new GetRecipeService();
    GetRecipeResult result = service.getRecipe(recipeName);

    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    return decoder.toJson(result);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting to get recipes.");
    GetHandler handler = new GetHandler();
    handler.handle(exchange, this);
  }
}
