package Handler.post;

import com.sun.net.httpserver.*;
import Handler.ObjectEncoder;

import java.io.*;
import java.net.*;

/**
 * handles requests that use the <code>POST</code> method.
 */
public class PostHandler {

  private final ObjectEncoder encoder = new ObjectEncoder();

  /**
   * handles the input from the server.
   * @param exchange the two-way communication with the website object.
   * @throws IOException Some kind of internal error has occurred inside the server
   */
  public void handle(HttpExchange exchange, Object handler) throws IOException {

    boolean success = false;

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

        InputStream reqBody = exchange.getRequestBody();
        String reqData = encoder.readString(reqBody);

        System.out.println(reqData);

        String returnString = handleAppropriateClass(exchange, handler, reqData);

        if (returnString.contains("\"success\": true")) success = true;

        System.out.println(returnString);

        if (success) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          System.out.println("HTTP_OK");
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          System.out.println("HTTP_BAD_REQUEST");
        }
        OutputStream respBody = exchange.getResponseBody();
        encoder.writeString(returnString, respBody);
        respBody.close();
      }
    }
    catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }

  }

  private String handleAppropriateClass(HttpExchange exchange, Object handler, String reqData) {
    String returnString = null;
    if (handler instanceof RegisterHandler) returnString = new RegisterHandler().handleRegister(reqData);
    else if (handler instanceof LoginHandler) returnString = new LoginHandler().handleLogin(reqData);
    else if (handler instanceof AddIngredientHandler) {
      String authToken = getAuthorization(exchange);
      returnString = new AddIngredientHandler().fulfillRequest(authToken, reqData);
    }
    else if (handler instanceof AddTaxHandler) {
      String authToken = getAuthorization(exchange);
      returnString = new AddTaxHandler().handleTax(authToken, reqData);
    }
    else if (handler instanceof ClearHandler) returnString = new ClearHandler().handleClear();
    return returnString;
  }

  private String getAuthorization(HttpExchange exchange) {
    Headers reqHeaders = exchange.getRequestHeaders();
    return reqHeaders.getFirst("Authorization");
  }

}

