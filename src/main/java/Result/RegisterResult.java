package Result;

import java.util.Objects;

/**
 * a class with a response about whether the user was registered
 */
public class RegisterResult extends Result{
  private String authToken;
  private String username;
  private String personID;

  /**
   * creates a success response object
   * @param authToken the new authToken
   * @param username the user's username
   * @param personID the user's personID
   * @param success the registration worked
   */
  public RegisterResult(String authToken, String username, String personID, boolean success) {
    super(success);
    this.authToken = authToken;
    this.username = username;
    this.personID = personID;
  }

  /**
   * creates a failure response object
   * @param success the registration didn't work
   * @param message error message about why it didn't work
   */
  public RegisterResult(boolean success, String message) {
    super(success, message);
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUsername() {
    return username;
  }

  public String getUserID() {
    return personID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    RegisterResult that = (RegisterResult) o;
    return Objects.equals(username, that.username) && Objects.equals(personID, that.personID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), authToken, username, personID);
  }
}
