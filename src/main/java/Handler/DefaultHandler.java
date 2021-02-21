package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;

public class DefaultHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) {
    boolean success = false;

    try {
      if (exchange.getRequestMethod().toLowerCase().equals("get")) {
        String urlPath = exchange.getRequestURI().toString();

        if (urlPath == null || urlPath.equals("/")) {
          urlPath = "/index.html";
        }

        if (urlPath.contains("..")) throw new AccessDeniedException("You cannot access those filesだよ！");

        String filePath = "web" + urlPath;

        File file = new File(filePath);

        if (file.exists()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream respBody = exchange.getResponseBody();
          Files.copy(file.toPath(), respBody);
          respBody.flush();
          respBody.close();

          success = true;
        }
        if (!success) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          exchange.getResponseBody().close();
        }
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
