package Handler;

import Service.IngredientService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Request.IngredientRequest;
import Result.IngredientResult;

public class IngredientHandler implements HttpHandler {

  ObjectEncoder encoder = new ObjectEncoder();

  public String fulfillRequest(String authToken, String jsonString) {
    Gson decoder = new GsonBuilder().setPrettyPrinting().create();
    IngredientRequest request = new IngredientRequest();
    IngredientService service = new IngredientService();

    try {
      //jsonString = encoder.addAuthTokenToJson(authToken, jsonString);
    }
    catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    }

    try {
      request = (IngredientRequest) encoder.toObject(jsonString, request);
    } catch (HandlerException e) {
      return "Error: Something went wrong in the login handler.";
    }

    IngredientResult result = service.addIngredient(request);

    return decoder.toJson(result);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    boolean success = false;

    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        Headers reqHeaders = exchange.getRequestHeaders();
        String authToken = reqHeaders.getFirst("Authorization");

        System.out.println(authToken);

        InputStream reqBody = exchange.getRequestBody();
        String reqData = encoder.readString(reqBody);

        System.out.println(reqData);

        String returnString = fulfillRequest(authToken, reqData);

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
