package Request;

import java.util.Objects;

/**
 * a class representing the request to create an account
 */
public class RegisterRequest {
  private String personID;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;

  /**
   * makes an empty request.
   */
  public RegisterRequest() {

  }

  /**
   * makes a request object with the information to register a new user
   * @param username the name used to login in the future
   * @param password the password used to login later
   * @param email the user's email address
   * @param firstName the user's first name
   * @param lastName the user's last name
   */
  public RegisterRequest(String username, String password, String email, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  /**
   * only used by the LoadRequest class. Makes a request object with the information to register a new user
   * @param personID the ID of the new user
   * @param username the name used to login in the future
   * @param password the password used to login later
   * @param email the user's email address
   * @param firstName the user's first name
   * @param lastName the user's last name
   */
  public RegisterRequest(String personID, String username, String password, String email, String firstName, String lastName) {
    this.personID = personID;
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String hasAllNecessaryFields() {
    if (this.username == null) return "username";
    if (this.password == null) return "password";
    if (this.email == null) return "email";
    if (this.firstName == null) return "first name";
    if (this.lastName == null) return "last name";
    return "大丈夫です";
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RegisterRequest request = (RegisterRequest) o;
    return Objects.equals(personID, request.personID) && Objects.equals(username, request.username) && Objects.equals(password, request.password) && Objects.equals(email, request.email) && Objects.equals(firstName, request.firstName) && Objects.equals(lastName, request.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personID, username, password, email, firstName, lastName);
  }
}
