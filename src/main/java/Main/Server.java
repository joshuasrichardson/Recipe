package Main;

import Handler.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;

/**
 * interacts with the user to add and retrieve recipes and ingredients
 */
public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    public void run(String portNumber) {

      System.out.println("Initializing HTTP Server");

      try {
        server = HttpServer.create(
                new InetSocketAddress(Integer.parseInt(portNumber)),
                MAX_WAITING_CONNECTIONS);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }

      server.setExecutor(null);

      System.out.println("Creating contexts");

      server.createContext("/user/register", new RegisterHandler());

      /*server.createContext("/user/login", new LoginHandler());

      server.createContext("/clear", new ClearHandler());

      server.createContext("/fill", new FillHandler());

      server.createContext("/recipes", new RecipeHandler());*/

      server.createContext("/ingredients", new IngredientHandler());

      server.createContext("/", new DefaultHandler());

      System.out.println("Starting server");

      server.start();

      System.out.println("Server started");
    }

}
