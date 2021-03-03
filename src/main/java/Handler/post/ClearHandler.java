package Handler.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import Result.ClearResult;
import Main.Server;
import Service.ClearService;

import java.io.IOException;
import java.util.logging.Level;

/**
 * handles clear requests.
 */
public class ClearHandler implements HttpHandler {

  /**
   * clears the tables and returns a success or failure message.
   * @return the result in json format.
   */
  public String handleClear() {
    ClearService service = new ClearService();
    ClearResult result = service.clear();

    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    return decoder.toJson(result);
  }

  /**
   * handles clear requests.
   * @param exchange the http exchange.
   * @throws IOException
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Server.logger.log(Level.INFO, "Starting clear.");
    PostHandler handler = new PostHandler();
    handler.handle(exchange, this);
  }
}