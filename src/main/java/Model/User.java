package Model;

import java.util.Objects;

/**
 * a class representing the user of the Family Map program.
 */
public class User {
  private String userID;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;

  /**
   * default constructor makes an empty user object.
   */
  public User() {

  }

  /**
   * makes a user with the following parameters and generates an ID.
   * @param userID the id of the user
   * @param username the name used to log in
   * @param password the password used to log in
   * @param email the user's email
   * @param firstName the user's first name
   * @param lastName the user's last name
   */
  public User(String userID, String username, String password, String email, String firstName, String lastName) {
    this.userID = userID;
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUserID() {
    return userID;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userID.equals(user.userID) && username.equals(user.username) && password.equals(user.password) && email.equals(user.email) && firstName.equals(user.firstName) && lastName.equals(user.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, username, password, email, firstName, lastName);
  }
}

