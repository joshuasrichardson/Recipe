package Handler.post;

import Handler.ObjectEncoder;
import Main.Server;
import Service.AddIngredientService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;

import Request.AddIngredientRequest;
import Result.AddIngredientResult;

public class AddIngredientHandler implements HttpHandler {

  ObjectEncoder encoder = new ObjectEncoder();

  public String fulfillRequest(String authToken, String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    AddIngredientRequest request = new AddIngredientRequest();
    AddIngredientService service = new AddIngredientService();

    request = (AddIngredientRequest) encoder.toObject(jsonString, request.getClass());

    AddIngredientResult result = service.addIngredient(authToken, request);

    return decoder.toJson(result);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting to add ingredient.");
    PostHandler handler = new PostHandler();
    handler.handle(exchange, this);
  }
}
