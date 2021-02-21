package Handler;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
  private ObjectEncoder encoder = new ObjectEncoder();

  public String handleJsonString(String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    RegisterRequest request = new RegisterRequest();
    RegisterService service = new RegisterService();

    try {
      request = (RegisterRequest) encoder.toObject(jsonString, request);
    } catch (HandlerException e) {
      return "Error: Something went wrong in the register handler.";
    }

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

    boolean success = false;

    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        InputStream reqBody = exchange.getRequestBody();
        String reqData = encoder.readString(reqBody);

        System.out.println(reqData);

        String returnString = handleJsonString(reqData);

        System.out.println(returnString);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream respBody = exchange.getResponseBody();
        encoder.writeString(returnString, respBody);

        respBody.close();

        success = true;
      }
      if (!success) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
      }
    }
    catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }

  }
}
