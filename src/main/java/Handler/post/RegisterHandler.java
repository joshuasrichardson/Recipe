package Handler.post;

import Handler.ObjectEncoder;
import Main.Server;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.logging.Level;

public class RegisterHandler implements HttpHandler {

  private ObjectEncoder encoder = new ObjectEncoder();

  /**
   * handles the incoming register request and returns a success or failure message in json format.
   * @param jsonString the request message.
   * @return the result message.
   */
  public String handleRegister(String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    RegisterRequest request = new RegisterRequest();
    RegisterService service = new RegisterService();

    try {
      if (!encoder.jsonMatchesObject(jsonString, RegisterRequest.class)) throw new InvalidParameterException();
    }
    catch (InvalidParameterException i) {
      RegisterResult failedResult = new RegisterResult(false, "Error: Information is either missing or formatted wrong.");
      return decoder.toJson(failedResult);
    }

    request = (RegisterRequest) encoder.toObject(jsonString, RegisterRequest.class);
    RegisterResult result = service.register(request);

    return decoder.toJson(result);
  }

  /**
   * handles the input from the server.
   * @param exchange the two-way communication with the website object.
   * @throws IOException Some kind of internal error has occurred inside the server
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting login.");
    PostHandler handler = new PostHandler();
    handler.handle(exchange, this);
  }
}
