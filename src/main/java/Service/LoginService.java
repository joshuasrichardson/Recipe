package Service;

import Database.*;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.logging.Level;

import static Server.Server.CONNECTION_URL;
import static Server.Server.logger;

/**
 * a class to ask the data access objects for an authorization token with a username and password.
 */
public class LoginService {

  private final String connection;

  Database db = new Database();

  public LoginService() {
    this.connection = CONNECTION_URL;
  }

  public LoginService(String connection) {
    this.connection = connection;
  }

  /**
   * receives a login request from the login handler and attempts to let the user login
   * through the UserDAO class.
   * @param loginRequest the information of the user to login.
   * @return an object with the feedback of the login attempt.
   */
  public LoginResult login(LoginRequest loginRequest) {
    if (loginRequest.hasAllNecessaryFields() == "大丈夫です") {
      try {
        UserDAO userDAO = new UserDAO(db.getConnection(connection));
        User user = userDAO.accessUserFromTable("username", loginRequest.getUsername(),
                "password", loginRequest.getPassword());

        AuthToken authToken = generateAuthToken(user.getUsername());
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection(connection));
        authTokenDAO.addAuthTokenToTable(authToken);

        return new LoginResult(authToken.getAuthTokenID(), user.getUsername(),
                user.getUsername(), true);
      }
      catch (DatabaseAccessException e) {
        return new LoginResult(false, "Error: " + e.getMessage());
      }
      catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      finally {
        try {
          db.closeConnection(true);
        }
        catch (SQLException throwables) {
          logger.log(Level.WARNING, "Couldn't close connection.");
          throwables.printStackTrace();
        }
      }
    }
    else return new LoginResult(false, "Error: Missing " + loginRequest.hasAllNecessaryFields() + ".");
    return null;
  }

  private static AuthToken generateAuthToken(String username) {
    LocalTime time = LocalTime.now();
    String authTokenID = username + time.toString();
    return new AuthToken(authTokenID, username);
  }
}
