package Model;

import java.util.Objects;

/**
 * a class representing a key used to access different methods for a user who has logged in to avoid
 * logging in repeatedly.
 */
public class AuthToken {
  private String authTokenID;
  private String username;

  /**
   * makes an empty AuthToken
   */
  public AuthToken() {

  }

  /**
   * makes an authToken for a user who logged in.
   * @param authTokenID the authorization token of the login.
   * @param username the person ID of the user.
   */
  public AuthToken(String authTokenID, String username) {
    this.authTokenID = authTokenID;
    this.username = username;
  }

  public String getAuthTokenID() {
    return authTokenID;
  }

  public String getUsername() {
    return username;
  }

  public void setAuthTokenID(String authTokenID) {
    this.authTokenID = authTokenID;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthToken authToken = (AuthToken) o;
    return authTokenID.equals(authToken.authTokenID) && username.equals(authToken.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authTokenID, username);
  }
}

