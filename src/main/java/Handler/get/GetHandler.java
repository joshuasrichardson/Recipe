package Handler.get;

import Handler.ObjectEncoder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * handles <code>GET</code> requests.
 */
public class GetHandler {

  ObjectEncoder encoder = new ObjectEncoder();

  /**
   * handles the exchange for handlers that use <code>GET</code> requests.
   * @param exchange the http exchange.
   * @param handler the handler that is calling this handler.
   */
  public void handle(HttpExchange exchange, Object handler) {
    boolean success = false;

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
        String urlPath = exchange.getRequestURI().toString();

        System.out.println(urlPath);

        String returnString = "";

        Headers header = exchange.getRequestHeaders();

        String authToken = header.getFirst("Authorization");

        if (handler instanceof GetIngredientHandler) {
          if (urlPath.equals("/ingredient")) header = header;//FIXME::implement get all ingredients.
          else returnString = new GetIngredientHandler().handleIngredient(getSecondPartOfURL(urlPath), authToken);
        }
        else if (handler instanceof GetRecipeHandler) {
          if (urlPath.equals("/recipes")) header = header;//FIXME::implement get all
          else returnString = new GetRecipeHandler().handleRecipe(getSecondPartOfURL(urlPath));
        }

        if (returnString.contains("\"success\": true")) success = true;

        System.out.println(returnString);

        if(success) {
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
      else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getSecondPartOfURL(String urlPath) {
    assert urlPath != null && urlPath.charAt(0) == '/';

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(urlPath.substring(urlPath.indexOf('/', 1)));
    stringBuilder.deleteCharAt(0);
    String nextPart;
    if (stringBuilder.toString().contains("/")) {
      nextPart = stringBuilder.substring(0, stringBuilder.indexOf("/"));
    }
    else {
      nextPart = stringBuilder.toString();
    }

    return nextPart;
  }

  public static String fixInput(String inputToFix) {
    return inputToFix.replace("%20", " ");
  }
}
