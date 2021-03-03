package Main;

import Handler.RecipeHandler;
import Handler.get.DefaultHandler;
import Handler.get.GetIngredientHandler;
import Handler.post.ClearHandler;
import Handler.post.AddIngredientHandler;
import Handler.post.LoginHandler;
import Handler.post.RegisterHandler;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * interacts with the user to add and retrieve recipes and ingredients
 */
public class Server {

  public static Logger logger;

  public final static String CONNECTION_URL = "jdbc:sqlite:storage.sqlite";

  private static final int MAX_WAITING_CONNECTIONS = 12;

  private HttpServer server;

  public void run(String portNumber) {

    initLogger();

    logger.log(Level.INFO, "Starting Server.");

    System.out.println("Initializing HTTP Server");

    try {
      server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    server.setExecutor(null);

    System.out.println("Creating contexts");

    server.createContext("/user/register", new RegisterHandler());

    server.createContext("/user/login", new LoginHandler());

    server.createContext("/clear", new ClearHandler());

    server.createContext("/recipes", new RecipeHandler());

    server.createContext("/ingredients/add", new AddIngredientHandler());

    server.createContext("/ingredients", new GetIngredientHandler());

    server.createContext("/", new DefaultHandler());

    System.out.println("Starting server");

    server.start();

    System.out.println("Server started");
  }

  private static void initLogger() {
    FileHandler fileHandler;

    try {
      logger = Logger.getLogger("recipeLogger");
      logger.setLevel(Level.CONFIG);
      logger.setUseParentHandlers(false);

      fileHandler = new FileHandler("recipeLog.txt", false);
      fileHandler.setLevel(Level.CONFIG);
      fileHandler.setFormatter(new SimpleFormatter());

      logger.addHandler(fileHandler);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
