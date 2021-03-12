package Service;

import Database.Database;
import Result.ClearResult;

import java.sql.SQLException;
import java.util.logging.Level;

import static Server.Server.CONNECTION_URL;
import static Server.Server.logger;

/**
 * a class that tells the data access objects to clear all the tables in the database.
 */
public class ClearService {

  private String connection;

  public ClearService() {
    this.connection = CONNECTION_URL;
  }

  public ClearService(String connection) {
    this.connection = connection;
  }

  /**
   * clears out all the data in the database through the UserDAO class.
   *
   * @return an object with the feedback of the clear attempt.
   */
  public ClearResult clear() {
    logger.log(Level.INFO, "Clearing tables.");

    try {
      Database db = new Database();
      db.openConnection(connection);
      db.clearAllTables();
      db.closeConnection(true);
      return new ClearResult(true, "Clear succeeded");
    } catch (SQLException e) {
      logger.log(Level.WARNING, "Couldn't close connection.");
      return new ClearResult(false, "Error: " + e.getMessage());
    }
  }

}
