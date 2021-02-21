package Result;

import java.util.Objects;

/**
 * a result class that has a message and boolean of whether the request succeeded and is inherited by other result classes.
 */
public abstract class Result {
  private boolean success;
  private String message;

  /**
   * creates a result response object
   * @param success
   */
  public Result(boolean success) {
    this.success = success;
  }

  /**
   * creates a failure response object
   * @param success it didn't work
   * @param message error message about why it didn't work
   */
  public Result(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return success == result.success && Objects.equals(message, result.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, message);
  }
}
