package Service;

import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Database.Database;
import Database.UserDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * a class to tell the data access objects to create a new user.
 */
public class RegisterService {

  private boolean commit = true;

  private final int DEFAULT_GENERATIONS = 4;

  /**
   * makes a normal RegisterService that commits its changes to the database when there are no errors.
   */
  public RegisterService() {

  }

  /**
   * makes a normal RegisterService that only commits its changes to the database if <code>commit</code> is true.
   * @param commit whether or not to commit the changes.
   */
  public RegisterService(boolean commit) {
    this.commit = commit;
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
        UserDAO userDAO = new UserDAO(db.getConnection("jdbc:sqlite:" +
                newUser.getUsername() + ".sqlite"));
        userDAO.createUserTable();
        userDAO.addUserToTable(newUser);

        userDAO.createAuthTokensTable();
        String authToken = generateAuthToken(newUser.getUsername());
        userDAO.addAuthTokenToTable(authToken, newUser.getUserID());

        return new RegisterResult(authToken, newUser.getUsername(), newUser.getUserID(), true);
      }
      catch (SQLException e) {
        return new RegisterResult(false, "Error: " + e.getMessage());
      }
      finally {
        try {
          db.closeConnection(commit);
        }
        catch (SQLException e) {
          return new RegisterResult(false, "Error: " + e.getMessage());
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
    newUser.setUserID(generateUserID(registerRequest.getLastName(), registerRequest.getUsername()));
    newUser.setUsername(registerRequest.getUsername());
    newUser.setPassword(registerRequest.getPassword());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setFirstName(registerRequest.getFirstName());
    newUser.setLastName(registerRequest.getLastName());

    return newUser;
  }

  private static String generateUserID(String lastName, String username) {
    return lastName + username;
  }

  private static String generateAuthToken(String username) {
    LocalDateTime dateTime = LocalDateTime.now();
    return username.hashCode() + dateTime.toString();
  }
}
