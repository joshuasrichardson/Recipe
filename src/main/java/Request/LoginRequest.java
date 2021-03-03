package Request;

import java.util.Objects;

/**
 * a class to represent the username and password on a login attempt.
 */
public class LoginRequest {
  private String username;
  private String password;

  /**
   * makes an empty login request.
   */
  public LoginRequest() {  }

  /**
   * makes a login request.
   * @param username the name used to login.
   * @param password the password used to login.
   */
  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  /**
   * determines whether all the needed fields exist.
   * @return a string of a missing value or a message saying it's okay.
   */
  public String hasAllNecessaryFields() {
    if (this.username == null) return "username";
    if (this.password == null) return "password";
    return "大丈夫です";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LoginRequest that = (LoginRequest) o;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }
}

