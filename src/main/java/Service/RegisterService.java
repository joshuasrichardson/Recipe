package Service;

import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Database.*;

import java.sql.SQLException;
import java.time.LocalTime;

import static Server.Server.CONNECTION_URL;

/**
 * a class to tell the data access objects to create a new user.
 */
public class RegisterService {

  private String connection;

  /**
   * makes a normal RegisterService that commits its changes to the database when there are no errors.
   */
  public RegisterService() { this.connection = CONNECTION_URL; }

  /**
   *
   * @param connection
   */
  public RegisterService(String connection) {
    this.connection = connection;
  }

  /**
   * receives a register request from the register handler, makes a user object, and registers the user in the database
   * through the UserDAO class.
   * @param registerRequest the information of the user to register
   * @return an object with the feedback of the register attempt
   */
  public RegisterResult register(RegisterRequest registerRequest) {
    RegisterResult registerResult = createAccount(registerRequest);

    return registerResult;
  }

  private RegisterResult createAccount(RegisterRequest registerRequest) {
    if (registerRequest.hasAllNecessaryFields().equals("大丈夫です")) {

      User newUser = createNewUser(registerRequest);
      Database db = new Database();

      try {
        UserDAO userDAO = new UserDAO(db.getConnection(connection));
        userDAO.createUserTable();
        userDAO.addUserToTable(newUser);

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection(connection));
        authTokenDAO.createAuthTokenTable();
        AuthToken authToken = generateAuthToken(newUser.getUsername());
        authTokenDAO.addAuthTokenToTable(authToken);

        return new RegisterResult(authToken.getAuthTokenID(), newUser.getUsername(), newUser.getUsername(), true);
      }
      catch (DatabaseAccessException e) {
        return new RegisterResult(false, "Error: " + e.getMessage());
      }
      catch (SQLException throwables) {
        throwables.printStackTrace();
        return new RegisterResult(false, "Error: " + throwables.getMessage());
      }
      finally {
        try {
          db.closeConnection(true);
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    else {
      return new RegisterResult(false, "Error: Something is wrong with the user's " +
              registerRequest.hasAllNecessaryFields() + ".");
    }
  }

  private User createNewUser(RegisterRequest registerRequest) {
    User newUser = new User();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setPassword(registerRequest.getPassword());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setFirstName(registerRequest.getFirstName());
    newUser.setLastName(registerRequest.getLastName());

    return newUser;
  }

  private static AuthToken generateAuthToken(String username) {
    LocalTime time = LocalTime.now();
    String authTokenID = username + time.toString();
    return new AuthToken(authTokenID, username);
  }
}
