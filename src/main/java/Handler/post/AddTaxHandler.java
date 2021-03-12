package Handler.post;

import Handler.ObjectEncoder;
import Server.Server;
import Request.AddTaxRequest;
import Result.AddTaxResult;
import Service.TaxService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;

public class AddTaxHandler implements HttpHandler {

  ObjectEncoder encoder = new ObjectEncoder();

  public String handleTax(String authToken, String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    AddTaxRequest request = new AddTaxRequest();
    TaxService service = new TaxService();

    request = (AddTaxRequest) encoder.toObject(jsonString, request.getClass());

    AddTaxResult result = service.addTax(authToken, request);

    return decoder.toJson(result);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting to add ingredient.");
    PostHandler handler = new PostHandler();
    handler.handle(exchange, this);
  }
}
