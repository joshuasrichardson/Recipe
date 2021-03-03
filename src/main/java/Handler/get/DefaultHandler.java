package Handler.get;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;

/**
 * handles the web page without any requests.
 */
public class DefaultHandler implements HttpHandler {

  /**
   * handles the website.
   * @param exchange the http exchange
   * @throws IOException
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
        String urlPath = exchange.getRequestURI().toString();

        if (urlPath == null || urlPath.equals("/")) {
          urlPath = "/index.html";
        }

        if (urlPath.contains("..")) throw new AccessDeniedException("You cannot access those filesだよ！");

        String filePath = "web" + urlPath;

        File file = new File(filePath);

        if (file.exists()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
          filePath = "web/HTML/404.html";
          file = new File(filePath);
        }
        OutputStream respBody = exchange.getResponseBody();
        Files.copy(file.toPath(), respBody);
        respBody.flush();
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
}
