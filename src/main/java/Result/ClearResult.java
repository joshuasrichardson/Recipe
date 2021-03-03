package Result;

/**
 * a class with a response about whether the database was cleared.
 */
public class ClearResult extends Result {

  /**
   * creates a response object.
   * @param success it didn't work.
   * @param message error message about why it didn't work.
   */
  public ClearResult(boolean success, String message) {
    super(success, message);
  }
}
