package Handler.post;

import Handler.ObjectEncoder;
import Server.Server;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.logging.Level;

public class LoginHandler implements HttpHandler {

  private final ObjectEncoder encoder = new ObjectEncoder();

  /**
   * handles the incoming login request and returns a success or failure message in json format.
   * @param jsonString the request message.
   * @return the result message.
   */
  public String handleLogin(String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    LoginRequest request = new LoginRequest();
    LoginService service = new LoginService();

    try {
      if (!encoder.jsonMatchesObject(jsonString, LoginRequest.class)) throw new InvalidParameterException();
    }
    catch (InvalidParameterException i) {
      LoginResult failedResult = new LoginResult(false, "Error: Information is either missing or formatted wrong.");
      return decoder.toJson(failedResult);
    }

    request = (LoginRequest) encoder.toObject(jsonString, LoginRequest.class);
    LoginResult result = service.login(request);

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
