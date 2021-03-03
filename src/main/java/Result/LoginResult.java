package Result;

import java.util.Objects;

/**
 * a class with a response about whether the user could login.
 */
public class LoginResult extends Result {
  private String authtoken;
  private String username;
  private String personID;

  /**
   * creates a success response object.
   * @param authtoken the new authToken.
   * @param username the user's username.
   * @param personID the user's personID.
   * @param success the login worked.
   */
  public LoginResult(String authtoken, String username, String personID, boolean success) {
    super(success);
    this.authtoken = authtoken;
    this.username = username;
    this.personID = personID;
  }

  /**
   * creates a failure response object.
   * @param success the login didn't work.
   * @param message error message about why it didn't work.
   */
  public LoginResult(boolean success, String message) {
    super(success, message);
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public String getUsername() {
    return username;
  }

  public String getPersonID() {
    return personID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LoginResult that = (LoginResult) o;
    return Objects.equals(username, that.username) && Objects.equals(personID, that.personID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authtoken, username, personID);
  }
}
