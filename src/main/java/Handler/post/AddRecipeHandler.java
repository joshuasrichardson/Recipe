package Handler.post;

import Handler.ObjectEncoder;
import Model.Recipe;
import Request.AddIngredientRequest;
import Result.AddIngredientResult;
import Result.AddRecipeResult;
import Result.GetRecipeResult;
import Server.Server;
import Service.AddIngredientService;
import Service.AddRecipeService;
import Service.GetRecipeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;

public class AddRecipeHandler implements HttpHandler {

  ObjectEncoder encoder = new ObjectEncoder();

  public String handleRecipe(String jsonString) {
    Recipe recipe = (Recipe) encoder.toObject(jsonString, Recipe.class);

    AddRecipeService service = new AddRecipeService();
    AddRecipeResult result = service.addRecipe(recipe);

    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    return decoder.toJson(result);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting to add a recipe.");
    PostHandler handler = new PostHandler();
    handler.handle(exchange, this);
  }
}
